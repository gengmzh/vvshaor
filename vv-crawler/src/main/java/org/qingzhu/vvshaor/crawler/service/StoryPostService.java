/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service;

import java.util.Date;

import org.qingzhu.vvshaor.crawler.config.PostStatus;
import org.qingzhu.vvshaor.crawler.model.StoryPost;
import org.qingzhu.vvshaor.crawler.utils.MurmurHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

/**
 * @author gengmaozhang01
 * @since 上午8:08:48
 */
@Service
public class StoryPostService {

	private static final Logger log = LoggerFactory.getLogger(StoryPostService.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * save story post
	 * 
	 * @author gengmaozhang01
	 * @since 下午6:29:39
	 * @param storyPost
	 * @return
	 */
	public boolean publish(StoryPost storyPost) {
		Preconditions.checkNotNull(storyPost);
		Preconditions.checkArgument(storyPost.getTitle() != null && !storyPost.getTitle().isEmpty());
		Preconditions.checkArgument(storyPost.getBrief() != null && !storyPost.getBrief().isEmpty());
		Preconditions.checkArgument(storyPost.getContent() != null && !storyPost.getContent().isEmpty());

		String hash = MurmurHash.hash(storyPost.getTitle() + "\t" + storyPost.getBrief() + "\t" + storyPost.getLink());
		Query query = Query.query(Criteria.where("hash").is(hash));
		boolean flag = mongoTemplate.exists(query, StoryPost.class);
		if (flag) {
			log.warn("already have story {}", storyPost);
			return false;
		}

		storyPost.setHash(hash).setPublishTime(new Date()).setPostStatus(PostStatus.ONLINE);
		mongoTemplate.insert(storyPost);
		log.info("add story {} to mongo", storyPost);
		return true;
	}

}
