/**
 * 
 */
package org.qingzhu.vvshaor.crawler.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;

/**
 * @author gengmaozhang01
 * @since 上午10:55:05
 */
public class CrawlResult implements Serializable {

	private static final long serialVersionUID = -7227314099822117775L;

	private List<StoryPost> posts;
	private List<PageLink> links;

	public CrawlResult() {
		this.posts = Lists.newArrayList();
		this.links = Lists.newArrayList();
	}

	/**
	 * @return the posts
	 */
	public List<StoryPost> getPosts() {
		return posts;
	}

	/**
	 * @param posts
	 *            the posts to set
	 */
	public void setPosts(List<StoryPost> posts) {
		this.posts = posts;
	}

	/**
	 * @return the links
	 */
	public List<PageLink> getLinks() {
		return links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	public void setLinks(List<PageLink> links) {
		this.links = links;
	}

	/**
	 * @author gengmaozhang01
	 * @since 下午2:34:28
	 * @return
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}

}
