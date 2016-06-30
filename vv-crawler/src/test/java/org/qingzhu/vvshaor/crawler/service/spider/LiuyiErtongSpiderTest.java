/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service.spider;

import org.junit.Assert;
import org.junit.Test;
import org.qingzhu.vvshaor.crawler.BaseTest;
import org.qingzhu.vvshaor.crawler.model.CrawlResult;
import org.qingzhu.vvshaor.crawler.model.PageLink;
import org.qingzhu.vvshaor.crawler.service.spider.LiuyiErtongSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author gengmaozhang01
 * @since 上午10:25:16
 */
public class LiuyiErtongSpiderTest extends BaseTest {

	@Autowired
	private LiuyiErtongSpider liuyiErtongSpider;

	@Value("${spider.61ertong.seed}")
	private String seed;
	@Value("${spider.61ertong.seedurl}")
	private String seedUrl;

	@Test
	public void test_crawl() throws Exception {
		PageLink link = new PageLink().setSeed(seed).setPageUrl(seedUrl).setDepth(0);
		CrawlResult result = liuyiErtongSpider.crawl(link);

		Assert.assertNotNull(result);
		this.println(result);
	}

}
