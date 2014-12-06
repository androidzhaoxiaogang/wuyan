package net.wecash.server.tag.funny;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author franklin.li
 * 
 */
public class FunnyTag {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String name;
	private int clazz;
	private int type;
	private Object value;
	private int category;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getClazz() {
		return clazz;
	}
	public void setClazz(int clazz) {
		this.clazz = clazz;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "FunnyTag [id=" + id + ", name=" + name + ", clazz=" + clazz
				+ ", type=" + type + ", value=" + value + ", category="
				+ category + "]";
	}
	
}
