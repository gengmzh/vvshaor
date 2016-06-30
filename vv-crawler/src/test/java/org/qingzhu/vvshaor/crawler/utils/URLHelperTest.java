/**
 * 
 */
package org.qingzhu.vvshaor.crawler.utils;

import org.junit.Test;

/**
 * @author gengmaozhang01
 * @since 上午9:41:55
 */
public class URLHelperTest {

	@Test
	public void test_getParent() {
		String url = "http://www.61ertong.com/wenxue/tonghuagushi/list_177_1.html";
		String parent = URLHelper.getParent(url);
		System.out.println(parent);
	}

	@Test
	public void test_parseHref() throws Exception {
		String referer = "http://www.61ertong.com/wenxue/tonghuagushi/list_177_1.html";
		String href = "list_177_2.html";

		String link = URLHelper.parseHref(referer, href);

		System.out.println(link);
	}

}
