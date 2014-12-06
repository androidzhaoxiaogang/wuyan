package net.wecash.server.bean;

public class PhoneAuthBean {
	private String phone;
	private String captcha;
	private Long createTime;
	
	public PhoneAuthBean(String phone, String captcha) {
		super();
		this.phone = phone;
		this.captcha = captcha;
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
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	@Override
	public String toString() {
		return "PhoneAuthBean ["
				+ (phone != null ? "phone=" + phone + ", " : "")
				+ (captcha != null ? "captcha=" + captcha + ", " : "")
				+ (createTime != null ? "createTime=" + createTime : "") + "]";
	}
	
}
