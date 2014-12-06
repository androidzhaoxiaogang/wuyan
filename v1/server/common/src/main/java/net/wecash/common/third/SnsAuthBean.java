package net.wecash.common.third;


public class SnsAuthBean {
	private String uid;
	private String token;
	private Long userId;
	private int type = 1;

	public SnsAuthBean(){
	}
	
	public SnsAuthBean(String uid, String token, Long userId, int type) {
		super();
		this.uid = uid;
		this.token = token;
		this.userId = userId;
		this.type = type;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SnsAuthBean [uid=" + uid + ", token=" + token + ", userId="
				+ userId + ", type=" + type + "]";
	}
	
}