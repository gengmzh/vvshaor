/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service;

import org.qingzhu.vvshaor.crawler.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author gengmaozhang01
 * @since 下午4:18:28
 */
public class MongoTemplateTest extends BaseTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	class Test {

		private String id;
		private String name;
		private int age;

		public Test(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		@Override
		public String toString() {
			return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
		}

	}

	@org.junit.Test
	public void test_mongo() throws Exception {
		Test t = new Test("gmz", 30);
		mongoTemplate.insert(t);
		this.println(t);

		Query query = new Query().addCriteria(Criteria.where("_id").is(t.getId()));
		t = mongoTemplate.findOne(query, Test.class);
		this.println(t);
	}

}
