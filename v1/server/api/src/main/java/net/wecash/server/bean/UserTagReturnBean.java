package net.wecash.server.bean;



public class UserTagReturnBean {

	private Integer id;
	private Integer weight;
	private String tagName;
	private Integer type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "UserTagReturnBean [id=" + id + ", weight=" + weight
				+ ", tagName=" + tagName + ", type=" + type + "]";
	}
	
	
	
	
}
