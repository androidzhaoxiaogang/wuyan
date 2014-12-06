package net.wecash.server.bean;

public class ActivityUpdateBean {

	
	private Long id;
	private String details;
	private String describe;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Override
	public String toString() {
		return "ActivityUpdateBean [id=" + id + ", details=" + details
				+ ", describe=" + describe + "]";
	}
	
}
