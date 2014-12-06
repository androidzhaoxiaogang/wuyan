package net.wecash.server.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ThirdLoginBean {
	@NotNull
	@Size(max=80)
	private String client_id;
	@NotNull
	@Size(max=80)
	private String client_secret;
	@NotNull
	@Size(max=80)
	private String uid;
	@NotNull
	@Size(max=80)
	private String third_token;
	private String channel_push_id;
	private String user_push_id;
	
	private Integer type = 1;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getThird_token() {
		return third_token;
	}
	public void setThird_token(String third_token) {
		this.third_token = third_token;
	}
	
	public String getChannel_push_id() {
		return channel_push_id;
	}
	public void setChannel_push_id(String channel_push_id) {
		this.channel_push_id = channel_push_id;
	}
	public String getUser_push_id() {
		return user_push_id;
	}
	public void setUser_push_id(String user_push_id) {
		this.user_push_id = user_push_id;
	}
	@Override
	public String toString() {
		return "ThirdLoginBean [client_id=" + client_id + ", client_secret="
				+ client_secret + ", uid=" + uid + ", third_token="
				+ third_token + ", channel_push_id=" + channel_push_id
				+ ", user_push_id=" + user_push_id + ", type=" + type + "]";
	}
	
	
}
