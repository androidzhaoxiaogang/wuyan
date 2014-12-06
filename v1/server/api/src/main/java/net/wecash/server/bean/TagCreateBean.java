package net.wecash.server.bean;

import javax.validation.constraints.NotNull;

public class TagCreateBean {

	@NotNull
    private String name;
	
	@NotNull
	private Integer weight;
	
	@NotNull
	private Integer type;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TagCreateBean [" + (name != null ? "name=" + name + ", " : "")
				+ (weight != null ? "weight=" + weight + ", " : "")
				+ (type != null ? "type=" + type : "") + "]";
	}

}
