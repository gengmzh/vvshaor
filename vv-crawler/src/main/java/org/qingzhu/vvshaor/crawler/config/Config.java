/**
 * 
 */
package org.qingzhu.vvshaor.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gengmaozhang01
 * @since 下午12:33:04
 */
@Component
public final class Config {

	@Value("${spider.polite}")
	private long spiderPolite = 10;

	@Value("${spider.timeout}")
	private int spiderTimeout;

	@Value("${spider.useragent}")
	private String spiderUserAgent;

	/**
	 * @return the spiderPolite
	 */
	public long getSpiderPolite() {
		return spiderPolite;
	}

	/**
	 * @return the spiderTimeout
	 */
	public int getSpiderTimeout() {
		return spiderTimeout;
	}

	/**
	 * @return the spiderUserAgent
	 */
	public String getSpiderUserAgent() {
		return spiderUserAgent;
	}

}
