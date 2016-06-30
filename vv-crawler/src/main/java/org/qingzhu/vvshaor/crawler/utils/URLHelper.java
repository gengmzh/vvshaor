/**
 * 
 */
package org.qingzhu.vvshaor.crawler.utils;

import java.net.URI;

/**
 * @author gengmaozhang01
 * @since 上午9:38:28
 */
public final class URLHelper {

	private URLHelper() {
	}

	public static String getParent(String url) {
		if (url == null) {
			return null;
		}
		int ei = url.lastIndexOf("/");
		if (ei > -1) {
			return url.substring(0, ei);
		}
		return url;
	}

	public static String parseHref(String referer, String href) throws Exception {
		if (referer == null || href == null) {
			return href;
		}
		String lower = href.toLowerCase();
		if (lower.startsWith("http://") || lower.startsWith("https://")) {
			return href;
		}
		if (href.startsWith("/")) {
			URI uri = new URI(referer);
			int port = uri.getPort();
			if (port < 0 || port == 80) {
				return uri.getScheme() + "://" + uri.getHost() + href;
			} else {
				return uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + href;
			}
		} else {
			return getParent(referer) + "/" + href;
		}
	}

}
