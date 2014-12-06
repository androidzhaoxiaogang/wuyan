package net.wecash.common.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String fromUsername;
	private String toUsername;
	private String content;
	private Integer type;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Message ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (fromUsername != null ? "fromUsername=" + fromUsername + ", "
						: "")
				+ (toUsername != null ? "toUsername=" + toUsername + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (type != null ? "type=" + type : "") + "]";
	}

}
