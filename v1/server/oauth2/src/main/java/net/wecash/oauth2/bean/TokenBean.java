package net.wecash.oauth2.bean;

import java.util.Date;

public class TokenBean {
	private String token;
	private String username;
	private Long expiresIn;
    private Date createTime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "TokenBean [" + (token != null ? "token=" + token + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (expiresIn != null ? "expiresIn=" + expiresIn + ", " : "")
				+ (createTime != null ? "createTime=" + createTime : "") + "]";
	}
    
}
