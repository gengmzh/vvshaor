/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.qingzhu.vvshaor.crawler.BaseTest;

/**
 * @author gengmaozhang01
 * @since 上午8:44:19
 */
public class SpiderServiceTest extends BaseTest {

	@Resource(name = "liuyiErtongSpiderService")
	private SpiderService liuyiErtongSpiderService;

	@Test
	public void test_run() throws Exception {
		liuyiErtongSpiderService.run();
	}

}
