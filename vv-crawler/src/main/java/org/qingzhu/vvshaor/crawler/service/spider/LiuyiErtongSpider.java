/**
 * 
 */
package org.qingzhu.vvshaor.crawler.service.spider;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.qingzhu.vvshaor.crawler.model.CrawlResult;
import org.qingzhu.vvshaor.crawler.model.PageLink;
import org.qingzhu.vvshaor.crawler.model.StoryPost;
import org.qingzhu.vvshaor.crawler.utils.URLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

/**
 * spider for 61ertong
 * 
 * @author gengmaozhang01
 * @since 上午11:30:42
 */
@Service
public class LiuyiErtongSpider implements Spider {

	private static final Logger log = LoggerFactory.getLogger(LiuyiErtongSpider.class);

	@Value("${spider.61ertong.seed}")
	private String seed;
	// @Value("${spider.61ertong.seedurl}")
	// private String seedUrl;
	@Value("${spider.61ertong.useragent}")
	private String userAgent;
	@Value("${spider.61ertong.timeout}")
	private int timeout;
	@Value("${spider.61ertong.depth}")
	private int depth;
	@Value("${spider.61ertong.polite}")
	private int polite;

	@Override
	public String getSeed() {
		return this.seed;
	}

	@Override
	public CrawlResult crawl(PageLink link) throws Exception {
		// args
		Preconditions
				.checkArgument(link != null && link.getSeed() != null && link.getPageUrl() != null, "link is null");
		Preconditions.checkArgument(link.getSeed().equals(this.seed), "seed " + seed + " is illegal");
		if (link.getDepth() > this.depth) {
			log.warn("link depth is illegal, and ignore it, link={}, depth={}", link, depth);
			return null;
		}

		CrawlResult result = new CrawlResult();
		// fetch
		Document doc = Jsoup.connect(link.getPageUrl()).userAgent(userAgent).timeout(timeout * 1000).get();
		// parse story
		Elements elements = doc.select("div.list_title");
		Element h3 = elements.select("h3").first();
		String type = h3 != null ? h3.text() : null;
		elements = doc.select("div.list").select("li");
		for (Element li : elements) {
			h3 = li.select("h3").first();
			Element a = h3.getElementsByTag("a").first();
			String title = CharMatcher.WHITESPACE.trimAndCollapseFrom(a.text(), ' ');
			String href = a.attr("href");
			Node text = h3.nextSibling();
			String brief = CharMatcher.WHITESPACE.trimAndCollapseFrom(text.toString(), ' ');
			StoryPost post = new StoryPost().setTitle(title).setBrief(brief).setType(type).setLink(href)
					.setCreateTime(new Date());
			result.getPosts().add(post);
			// log.info("get story: {}", post);
		}
		log.info("found {} story posts", result.getPosts().size());
		// parse link
		if (link.getDepth() < this.depth) {
			elements = doc.select("div.pagelist").select("div.leaf");
			for (Element div : elements) {
				Element a = div.getElementsByTag("a").first();
				String href = URLHelper.parseHref(link.getPageUrl(), a.attr("href"));
				PageLink other = new PageLink().setSeed(link.getSeed()).setSeries(link.getSeries()).setPageUrl(href)
						.setDepth(link.getDepth() + 1).setCatchTime(new Date());
				result.getLinks().add(other);
			}
		}
		log.info("found {} story links", result.getLinks().size());
		// parse content
		for (StoryPost post : result.getPosts()) {
			doc = Jsoup.connect(post.getLink()).userAgent(userAgent).timeout(timeout * 1000).get();
			// author
			Element div = doc.select("div.tt").first();
			String subhead = div.text();
			int si = subhead.indexOf("作者："), ei = subhead.indexOf("来源：");
			if (si > -1 && ei > -1) {
				String author = subhead.substring(si + 3, ei);
				author = CharMatcher.WHITESPACE.trimAndCollapseFrom(author, ' ');
				post.setAuthor(author);
			}
			// content
			div = doc.select("div.content").first();
			StringBuffer content = new StringBuffer();
			for (Element p : div.getElementsByTag("p")) {
				String section = CharMatcher.WHITESPACE.trimAndCollapseFrom(p.text(), ' ');
				content.append("<p>").append(section).append("</p>");
			}
			post.setContent(content.toString());
			// sleep
			try {
				Thread.sleep(this.polite * 1000);
			} catch (Exception ex) {
				log.error("sleep interrupted", ex);
			}
		}
		return result;
	}

}
