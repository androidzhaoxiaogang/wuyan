/**
 * 
 */
package net.wecash.server.tag.funny.Bean;

import javax.validation.constraints.NotNull;

/**
 * @author franklin.li
 * 
 */
public class FunnyTagCreateBean {
	@NotNull
	private String name;
	@NotNull
	private int clazz;
	@NotNull
	private int type;
	private Object value;
	private int category;
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
		return "FunnyTagCreateBean [name=" + name + ", clazz=" + clazz
				+ ", type=" + type + ", value=" + value + ", category="
				+ category + "]";
	}
	
	
}
