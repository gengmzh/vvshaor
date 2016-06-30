/**
 * 
 */
package org.qingzhu.vvshaor.crawler.config;

/**
 * @author gengmaozhang01
 * @since 下午1:06:56
 */
public enum LinkStatus {

	INITIAL(1, "初始化"), SUCCESS(2, "抓取成功"), FAILUER(3, "抓取失败");

	private int code;
	private String title;

	private LinkStatus(int code, String title) {
		this.code = code;
		this.title = title;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	public static LinkStatus of(int code) {
		for (LinkStatus ls : LinkStatus.values()) {
			if (ls.getCode() == code) {
				return ls;
			}
		}
		return null;
	}

}
