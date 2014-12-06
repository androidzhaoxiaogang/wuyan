package net.wecash.server.bean;

import javax.validation.constraints.NotNull;

public class PushCreateBean {

	@NotNull
	private String channelId;
	@NotNull
	private String userPushId;
	@NotNull
	private Integer deviceType;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getUserPushId() {
		return userPushId;
	}
	public void setUserPushId(String userPushId) {
		this.userPushId = userPushId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	@Override
	public String toString() {
		return "PushCreateBean [channelId=" + channelId + ", userPushId="
				+ userPushId + ", deviceType=" + deviceType + "]";
	}
	
	
	
	
	
}
