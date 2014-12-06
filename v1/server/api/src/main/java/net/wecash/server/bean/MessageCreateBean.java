package net.wecash.server.bean;

import javax.validation.constraints.NotNull;

public class MessageCreateBean {

	@NotNull
	private Integer fromUserId;
	@NotNull
	private Integer toUserId;
	@NotNull
	private String content;
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
