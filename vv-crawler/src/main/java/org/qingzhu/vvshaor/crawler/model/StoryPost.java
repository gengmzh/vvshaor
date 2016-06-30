/**
 * 
 */
package org.qingzhu.vvshaor.crawler.model;

import java.io.Serializable;
import java.util.Date;

import org.qingzhu.vvshaor.crawler.config.PostStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;

/**
 * story post
 * 
 * @author gengmaozhang01
 * @since 上午10:51:10
 */
public class StoryPost implements Serializable {

	private static final long serialVersionUID = 7634948950103889783L;

	private String id;
	private String title;
	private String brief;
	private String type;
	private String link;
	private String author;
	private String content;
	private Date createTime;
	private Date publishTime;
	private String hash;
	private int status;

	public StoryPost() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public StoryPost setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public StoryPost setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * @param brief
	 *            the brief to set
	 */
	public StoryPost setBrief(String brief) {
		this.brief = brief;
		return this;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public StoryPost setType(String type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public StoryPost setLink(String link) {
		this.link = link;
		return this;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public StoryPost setAuthor(String author) {
		this.author = author;
		return this;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public StoryPost setContent(String content) {
		this.content = content;
		return this;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public StoryPost setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public StoryPost setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
		return this;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash
	 *            the hash to set
	 */
	public StoryPost setHash(String hash) {
		this.hash = hash;
		return this;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public StoryPost setStatus(int status) {
		this.status = status;
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}

	public PostStatus getPostStatus() {
		return PostStatus.of(this.status);
	}

	public StoryPost setPostStatus(PostStatus status) {
		Preconditions.checkNotNull(status);
		this.status = status.getCode();
		return this;
	}

}
