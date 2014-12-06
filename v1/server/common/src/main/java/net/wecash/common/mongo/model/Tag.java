package net.wecash.common.mongo.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String num;
	private String tag;
	private Integer type;
	private Integer weight;
	private Long createTime;
	
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Tag [" + (id != null ? "id=" + id + ", " : "")
				+ (num != null ? "num=" + num + ", " : "")
				+ (tag != null ? "tag=" + tag + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (weight != null ? "weight=" + weight + ", " : "")
				+ (createTime != null ? "createTime=" + createTime : "") + "]";
	}
	
}
