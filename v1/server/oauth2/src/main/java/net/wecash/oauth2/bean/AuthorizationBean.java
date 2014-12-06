package net.wecash.oauth2.bean;

public class AuthorizationBean {
	private String accessToken;
	private String refreshToken;
	private String authzCode;
	private String username;
	private String password;
	private String ip;
	private String grantType;
	public String getAuthzCode() {
		return authzCode;
	}
	public void setAuthzCode(String authzCode) {
		this.authzCode = authzCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	@Override
	public String toString() {
		return "AuthorizationBean ["
				+ (authzCode != null ? "authzCode=" + authzCode + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (ip != null ? "ip=" + ip + ", " : "")
				+ (grantType != null ? "grantType=" + grantType : "") + "]";
	}
	
}
