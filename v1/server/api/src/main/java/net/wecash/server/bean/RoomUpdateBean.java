/**
 * 
 */
package net.wecash.server.bean;


/**
 * @author franklin.li
 * 
 */
public class RoomUpdateBean {
	private Integer minPrice;
	private Integer maxPrice;
	private Float lat;
	private Float lng;
	private String description;
	private String userId;
	private String province;
	private String city;
	private String area;
	private String landmark;
	private String lineNum;
	private String lineStation;
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	public Integer getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}
	public String getLineStation() {
		return lineStation;
	}
	public void setLineStation(String lineStation) {
		this.lineStation = lineStation;
	}
	@Override
	public String toString() {
		return "RoomUpdateBean [minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", lat=" + lat + ", lng=" + lng + ", description="
				+ description + ", userId=" + userId + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", landmark="
				+ landmark + ", lineNum=" + lineNum + ", lineStation="
				+ lineStation + "]";
	}
	
}
