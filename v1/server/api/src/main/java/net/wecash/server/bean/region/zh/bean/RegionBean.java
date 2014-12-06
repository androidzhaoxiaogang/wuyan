package net.wecash.server.bean.region.zh.bean;

public class RegionBean {

	private String province;
	private String city;
	private String area;
	private String landmark;
	
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	@Override
	public String toString() {
		return "RegionNameBean [province=" + province + ", city=" + city
				+ ", area=" + area + ", landmark=" + landmark + "]";
	}
	
}
