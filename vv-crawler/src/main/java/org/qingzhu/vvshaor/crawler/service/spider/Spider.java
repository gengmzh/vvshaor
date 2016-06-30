/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service.spider;

import org.qingzhu.vvshaor.crawler.model.CrawlResult;
import org.qingzhu.vvshaor.crawler.model.PageLink;

/**
 * @author gengmaozhang01
 * @since 上午11:26:36
 */
public interface Spider {

	public String getSeed();

	public CrawlResult crawl(PageLink link) throws Exception;

}
