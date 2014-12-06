package net.wecash.server.bean;

public class UserLoginResultBean {
	private String token;
	private Long expires_in;
	private Long userId;
	private String clientId;
	private Boolean firstLogin;
	private Float infoIntegrityLevel;
	
	public UserLoginResultBean(String token, Long expires_in, Long userId,
			String clientId, Boolean firstLogin, Float infoIntegrityLevel) {
		super();
		this.token = token;
		this.expires_in = expires_in;
		this.userId = userId;
		this.clientId = clientId;
		this.firstLogin = firstLogin;
		this.infoIntegrityLevel = infoIntegrityLevel;
	}
	
	public UserLoginResultBean(String token, Long expires_in, Long userId,
			String clientId, Boolean firstLogin) {
		super();
		this.token = token;
		this.expires_in = expires_in;
		this.userId = userId;
		this.clientId = clientId;
		this.firstLogin = firstLogin;
	}
	
	public UserLoginResultBean(String token, Long expires_in, Long userId,
			String clientId) {
		super();
		this.token = token;
		this.expires_in = expires_in;
		this.userId = userId;
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Boolean getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Float getInfoIntegrityLevel() {
		return infoIntegrityLevel;
	}

	public void setInfoIntegrityLevel(Float infoIntegrityLevel) {
		this.infoIntegrityLevel = infoIntegrityLevel;
	}

	@Override
	public String toString() {
		return "UserLoginResultBean [token=" + token + ", expires_in="
				+ expires_in + ", userId=" + userId + ", clientId=" + clientId
				+ ", firstLogin=" + firstLogin + ", infoIntegrityLevel="
				+ infoIntegrityLevel + "]";
	}
	
}
