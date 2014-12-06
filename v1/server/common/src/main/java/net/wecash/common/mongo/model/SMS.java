package net.wecash.common.mongo.model;

import java.util.Arrays;

public class SMS {
	private String phone;
	private String content;
	private String[] param;
	private Long createTime;
	
	public SMS(String phone, String[] param) {
		super();
		this.phone = phone;
		this.param = param;
	}
	public SMS() {
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getParam() {
		return param;
	}
	public void setParam(String[] param) {
		this.param = param;
	}
	@Override
	public String toString() {
		return "SMS ["
				+ (phone != null ? "phone=" + phone + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (param != null ? "param=" + Arrays.toString(param) + ", "
						: "")
				+ (createTime != null ? "createTime=" + createTime : "") + "]";
	}

}
