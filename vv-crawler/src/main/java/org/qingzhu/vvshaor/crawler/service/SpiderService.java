/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service;

import java.util.Date;

import org.qingzhu.vvshaor.crawler.config.Config;
import org.qingzhu.vvshaor.crawler.config.LinkStatus;
import org.qingzhu.vvshaor.crawler.model.CrawlResult;
import org.qingzhu.vvshaor.crawler.model.PageLink;
import org.qingzhu.vvshaor.crawler.model.StoryPost;
import org.qingzhu.vvshaor.crawler.service.spider.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

/**
 * @author gengmaozhang01
 * @since 上午7:48:17
 */
// @Service
public class SpiderService implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(SpiderService.class);

	private Spider spider;
	private PageLink seedLink;

	@Autowired
	private PageLinkService pageLinkService;
	@Autowired
	private StoryPostService storyPostService;
	@Autowired
	private Config config;

	public SpiderService(Spider spider, PageLink seedLink) {
		this.spider = spider;
		this.seedLink = seedLink;
	}

	@Override
	public void run() {
		String name = this.spider.getClass().getSimpleName();
		log.info("Spider[{}] starts...", name);
		try {
			this.init();
			log.info("seed link {} offered", seedLink);

			long count = 0;
			boolean flag = true;
			do {
				flag = this.crawl();
				count++;
				try {
					Thread.sleep(config.getSpiderPolite() * 1000);
				} catch (Exception ex) {
					log.error("sleep interrupted", ex);
				}
			} while (flag);
			log.info("total {} links crawled", count);
		} catch (Exception ex) {
			log.error("crawl failed", ex);
		}
		log.info("Spider[{}] finished", name);
	}

	private void init() throws Exception {
		Preconditions.checkNotNull(seedLink);
		seedLink.setSeries(System.currentTimeMillis() / 1000).setCatchTime(new Date())
				.setLinkStatus(LinkStatus.INITIAL);
		pageLinkService.offer(seedLink);
	}

	private boolean crawl() throws Exception {
		PageLink link = null;
		try {
			link = pageLinkService.poll(spider.getSeed());
		} catch (Exception ex) {
			log.error("poll link failed", ex);
		}
		if (link == null) {
			log.warn("no link to crawl, and break");
			return false;
		}

		log.info("poll link {}", link);
		CrawlResult result = this.spider.crawl(link);
		link.setCrawlTime(new Date());
		if (result == null) {
			link.setLinkStatus(LinkStatus.FAILUER);
			pageLinkService.save(link);
			log.warn("crawl result is null, and ignore");
		} else {
			link.setLinkStatus(LinkStatus.SUCCESS);
			pageLinkService.save(link);
			log.info("crawl result, {} posts, {} links", result.getPosts().size(), result.getLinks().size());
			for (PageLink other : result.getLinks()) {
				try {
					pageLinkService.offer(other);
				} catch (Exception ex) {
					log.error("offer link failed, {}", other);
				}
			}
			for (StoryPost story : result.getPosts()) {
				try {
					storyPostService.publish(story);
				} catch (Exception ex) {
					log.error("save story post failed", ex);
				}
			}
		}

		return true;
	}

}
