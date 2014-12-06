package net.wecash.common.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author xkk
 *
 */
public class Payment {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String username;
	private Integer type;
	private Float factor;
	private String desc;
	private Integer state;
	private Float remain;
	private Long createTime;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Float getFactor() {
		return factor;
	}
	public void setFactor(Float factor) {
		this.factor = factor;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Float getRemain() {
		return remain;
	}
	public void setRemain(Float remain) {
		this.remain = remain;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Payment [" + (id != null ? "id=" + id + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (factor != null ? "factor=" + factor + ", " : "")
				+ (desc != null ? "desc=" + desc + ", " : "")
				+ (state != null ? "state=" + state + ", " : "")
				+ (remain != null ? "remain=" + remain + ", " : "")
				+ (createTime != null ? "createTime=" + createTime : "") + "]";
	}
	
}
