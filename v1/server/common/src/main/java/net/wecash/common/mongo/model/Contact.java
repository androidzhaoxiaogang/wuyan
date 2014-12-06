package net.wecash.common.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String fromUsername;
	private String toUsername;
	private Boolean permit;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	public Boolean getPermit() {
		return permit;
	}
	public void setPermit(Boolean permit) {
		this.permit = permit;
	}
	@Override
	public String toString() {
		return "Contact ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (fromUsername != null ? "fromUsername=" + fromUsername + ", "
						: "")
				+ (toUsername != null ? "toUsername=" + toUsername + ", " : "")
				+ (permit != null ? "permit=" + permit : "") + "]";
	}

}

