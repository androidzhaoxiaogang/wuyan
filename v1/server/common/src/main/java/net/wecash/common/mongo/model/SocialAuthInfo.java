package net.wecash.common.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SocialAuthInfo {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String username;
	private String txId;
	private String txToken;
	private String sinaId;
	private String sinaToken;
	private String renrenId;
	private String renrenToken;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTxId() {
		return txId;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getTxToken() {
		return txToken;
	}
	public void setTxToken(String txToken) {
		this.txToken = txToken;
	}
	public String getSinaId() {
		return sinaId;
	}
	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}
	public String getSinaToken() {
		return sinaToken;
	}
	public void setSinaToken(String sinaToken) {
		this.sinaToken = sinaToken;
	}
	public String getRenrenId() {
		return renrenId;
	}
	public void setRenrenId(String renrenId) {
		this.renrenId = renrenId;
	}
	public String getRenrenToken() {
		return renrenToken;
	}
	public void setRenrenToken(String renrenToken) {
		this.renrenToken = renrenToken;
	}
	@Override
	public String toString() {
		return "SocialAuthInfo [" + (id != null ? "id=" + id + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (txId != null ? "txId=" + txId + ", " : "")
				+ (txToken != null ? "txToken=" + txToken + ", " : "")
				+ (sinaId != null ? "sinaId=" + sinaId + ", " : "")
				+ (sinaToken != null ? "sinaToken=" + sinaToken + ", " : "")
				+ (renrenId != null ? "renrenId=" + renrenId + ", " : "")
				+ (renrenToken != null ? "renrenToken=" + renrenToken : "")
				+ "]";
	}

}
