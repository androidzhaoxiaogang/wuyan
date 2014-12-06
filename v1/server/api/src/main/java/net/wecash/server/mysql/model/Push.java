package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.wecash.common.service.Collections;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = Collections.T_PUSH)
public class Push implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5302612185699728141L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	@Column(name="channel_id")
	private String channelId;
	@Column(name="user_push_id")
	private String userPushId;
	@Column(name="device_type")
	private Integer deviceType;
	@Column(name="user_id")
	private Long userId;
	public Push(){}
	public Push(String channelId, String userPushId, Integer deviceType,
			Long userId) {
		super();
		this.channelId = channelId;
		this.userPushId = userPushId;
		this.deviceType = deviceType;
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getUserPushId() {
		return userPushId;
	}
	public void setUserPushId(String userPushId) {
		this.userPushId = userPushId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Push [id=" + id + ", channelId=" + channelId + ", userPushId="
				+ userPushId + ", deviceType=" + deviceType + ", userId="
				+ userId + "]";
	}
	
	
	
	
	
	
}
