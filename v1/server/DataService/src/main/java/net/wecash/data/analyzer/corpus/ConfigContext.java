package net.wecash.data.analyzer.corpus;

public class ConfigContext {
	private int type;
	private String uid;
	private String token;
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
	@Override
	public String toString() {
		return "ConfigContext [type=" + type + ", uid=" + uid + ", token="
				+ token + "]";
	}
	
}
