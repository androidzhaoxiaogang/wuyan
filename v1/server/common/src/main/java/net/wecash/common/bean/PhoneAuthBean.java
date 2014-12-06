package net.wecash.common.bean;

public class PhoneAuthBean {
	private String phone;
	private String code;
	private Long createTime;
	
	public PhoneAuthBean(String phone, String code) {
		super();
		this.phone = phone;
		this.code = code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "PhoneAuthBean ["
				+ (code != null ? "code=" + code + ", " : "")
				+ (createTime != null ? "createTime=" + createTime : "") + "]";
	}
	
}
