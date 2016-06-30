package org.qingzhu.vvshaor.crawler;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * base spring test
 * 
 * @author gengmaozhang01
 * @since 2015年7月16日 上午11:57:51
 *
 */
@ActiveProfiles("DEV")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-context-test.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {

	protected static final Logger log = LoggerFactory.getLogger("BaseTest");

	protected void println(Object... values) {
		log.info("");
		for (int i = 0; i < values.length; i++) {
			log.info("{}", values[i]);
		}
		log.info("");
	}

}
