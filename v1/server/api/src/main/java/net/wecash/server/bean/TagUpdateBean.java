package net.wecash.server.bean;

import javax.validation.constraints.NotNull;

public class TagUpdateBean {

	@NotNull
	private String name;
	@NotNull
	private Integer weight;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "TagUpdateBean [" + (name != null ? "name=" + name + ", " : "")
				+ (weight != null ? "weight=" + weight : "") + "]";
	}
}
