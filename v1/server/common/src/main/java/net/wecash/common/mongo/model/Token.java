package net.wecash.common.mongo.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author xkk
 *
 */
public class Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6625873207897125544L;
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String username;
	private String ip;
	private String name;
	private Long expiresIn;
	private Long createTime;
	private String token;
	private int type;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Token ["
				+ (username != null ? "username=" + username + ", " : "")
				+ (ip != null ? "ip=" + ip + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (expiresIn != null ? "expiresIn=" + expiresIn + ", " : "")
				+ (createTime != null ? "createTime=" + createTime + ", " : "")
				+ (token != null ? "token=" + token + ", " : "") + "type="
				+ type + "]";
	}
	
}
