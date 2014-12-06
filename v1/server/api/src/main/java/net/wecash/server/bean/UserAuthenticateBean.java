package net.wecash.server.bean;

public class UserAuthenticateBean {
	private String username;
	private String password;
	private String ip;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "UserAuthenticateBean ["
				+ (username != null ? "username=" + username + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (ip != null ? "ip=" + ip : "") + "]";
	}
	
}
