/**
 * 
 */
package org.qingzhu.vvshaor.crawler.model;

import java.io.Serializable;
import java.util.Date;

import org.qingzhu.vvshaor.crawler.config.LinkStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;

/**
 * @author gengmaozhang01
 * @since 上午10:18:03
 */
public class PageLink implements Serializable {

	private static final long serialVersionUID = -7823105747211655125L;

	private String id;
	private String seed;
	private long series;
	private String pageUrl;
	private int depth;
	private Date catchTime;
	private Date crawlTime;
	private String hash;
	private int status;

	public PageLink() {
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
	public PageLink setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the seed
	 */
	public String getSeed() {
		return seed;
	}

	/**
	 * @param seed
	 *            the seed to set
	 */
	public PageLink setSeed(String seed) {
		this.seed = seed;
		return this;
	}

	/**
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * @param pageUrl
	 *            the pageUrl to set
	 */
	public PageLink setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
		return this;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public PageLink setDepth(int depth) {
		this.depth = depth;
		return this;
	}

	/**
	 * @return the series
	 */
	public long getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public PageLink setSeries(long series) {
		this.series = series;
		return this;
	}

	/**
	 * @return the catchTime
	 */
	public Date getCatchTime() {
		return catchTime;
	}

	/**
	 * @param catchTime
	 *            the catchTime to set
	 */
	public PageLink setCatchTime(Date catchTime) {
		this.catchTime = catchTime;
		return this;
	}

	/**
	 * @return the crawlTime
	 */
	public Date getCrawlTime() {
		return crawlTime;
	}

	/**
	 * @param crawlTime
	 *            the crawlTime to set
	 */
	public PageLink setCrawlTime(Date crawlTime) {
		this.crawlTime = crawlTime;
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
	public PageLink setHash(String hash) {
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
	public PageLink setStatus(int status) {
		this.status = status;
		return this;
	}

	/**
	 * @author gengmaozhang01
	 * @since 下午2:32:52
	 * @return
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}

	public LinkStatus getLinkStatus() {
		return LinkStatus.of(this.status);
	}

	public PageLink setLinkStatus(LinkStatus status) {
		Preconditions.checkNotNull(status);
		this.status = status.getCode();
		return this;
	}

}
