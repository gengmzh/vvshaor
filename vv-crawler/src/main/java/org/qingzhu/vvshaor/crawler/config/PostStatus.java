/**
 * 
 */
package org.qingzhu.vvshaor.crawler.config;

/**
 * @author gengmaozhang01
 * @since 下午1:06:56
 */
public enum PostStatus {

	INITIAL(1, "初始化"), ONLINE(2, "已发布"), OFFLINE(3, "已下线");

	private int code;
	private String title;

	private PostStatus(int code, String title) {
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

	public static PostStatus of(int code) {
		for (PostStatus ls : PostStatus.values()) {
			if (ls.getCode() == code) {
				return ls;
			}
		}
		return null;
	}

}
