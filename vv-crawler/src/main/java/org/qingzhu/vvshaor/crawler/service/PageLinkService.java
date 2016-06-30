/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.qingzhu.vvshaor.crawler.config.LinkStatus;
import org.qingzhu.vvshaor.crawler.model.PageLink;
import org.qingzhu.vvshaor.crawler.utils.MurmurHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.mongodb.WriteResult;

/**
 * @author gengmaozhang01
 * @since 下午4:54:14
 */
@Service
public class PageLinkService {

	private static final Logger log = LoggerFactory.getLogger(PageLinkService.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	private Map<String, BlockingQueue<PageLink>> pageLinkQueues;

	public PageLinkService() {
		this.pageLinkQueues = Maps.newConcurrentMap();
	}

	/**
	 * poll page link
	 * 
	 * @author gengmaozhang01
	 * @since 下午2:42:55
	 * @param seed
	 * @return
	 */
	public PageLink poll(String seed) {
		Preconditions.checkNotNull(seed);

		if (!pageLinkQueues.containsKey(seed)) {
			return null;
		}

		BlockingQueue<PageLink> queue = pageLinkQueues.get(seed);
		if (queue == null) {
			return null;
		}
		try {
			return queue.poll(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("poll page link failed", e);
			return null;
		}
	}

	/**
	 * add page link if [seed, series, pageUrl] not exists
	 * 
	 * @author gengmaozhang01
	 * @since 下午2:21:23
	 * @param pageLink
	 * @return
	 */
	public boolean offer(PageLink pageLink) {
		Preconditions.checkNotNull(pageLink);
		Preconditions.checkArgument(pageLink.getSeed() != null && !pageLink.getSeed().isEmpty());
		Preconditions.checkArgument(pageLink.getSeries() > 0);
		Preconditions.checkArgument(pageLink.getPageUrl() != null && !pageLink.getPageUrl().isEmpty());

		String hash = MurmurHash.hash(pageLink.getSeed() + "\t" + pageLink.getSeries() + "\t" + pageLink.getPageUrl());
		Query query = Query.query(Criteria.where("hash").is(hash));
		boolean flag = mongoTemplate.exists(query, PageLink.class);
		if (flag) {
			log.warn("already have link {}", pageLink);
			return false;
		}

		pageLink.setHash(hash).setLinkStatus(LinkStatus.INITIAL);
		mongoTemplate.insert(pageLink);
		log.info("add new link {} to mongo", pageLink);

		if (!pageLinkQueues.containsKey(pageLink.getSeed())) {
			synchronized (this.getClass()) {
				if (!pageLinkQueues.containsKey(pageLink.getSeed())) {
					pageLinkQueues.put(pageLink.getSeed(), new LinkedBlockingQueue<PageLink>());
				}
			}
		}
		try {
			BlockingQueue<PageLink> queue = pageLinkQueues.get(pageLink.getSeed());
			queue.offer(pageLink, 10, TimeUnit.SECONDS);
			log.info("now total {} links in queue of {}", queue.size(), pageLink.getSeed());
		} catch (InterruptedException e) {
			log.error("offer link %s to queue failed", e);
		}

		return true;
	}

	/**
	 * save page link
	 * 
	 * @author gengmaozhang01
	 * @since 下午3:13:40
	 * @param pageLink
	 */
	public void save(PageLink pageLink) {
		Preconditions.checkNotNull(pageLink);
		Preconditions.checkArgument(pageLink.getId() != null && pageLink.getSeed() != null && pageLink.getSeries() > 0
				&& pageLink.getPageUrl() != null);

		Query query = Query.query(Criteria.where("_id").is(pageLink.getId())
				.orOperator(Criteria.where("hash").is(pageLink.getHash())));
		Update update = Update.update("seed", pageLink.getSeed()).set("series", pageLink.getSeries())
				.set("pageUrl", pageLink.getPageUrl()).set("depth", pageLink.getDepth())
				.set("catchTime", pageLink.getCatchTime()).set("crawlTime", pageLink.getCrawlTime())
				.set("hash", pageLink.getHash()).set("status", pageLink.getStatus());
		WriteResult result = mongoTemplate.upsert(query, update, PageLink.class);

		log.info("save result {}", result);
	}

}
