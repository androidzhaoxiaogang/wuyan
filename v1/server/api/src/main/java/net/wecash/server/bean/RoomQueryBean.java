package net.wecash.server.bean;

public class RoomQueryBean {

	
	private Integer userId;
	private String useName;
	private Character gender;
	private String phone;
	private Integer minPrice;
	private Integer maxPrice;
	private String province;
	private String city;
	private String area;
	private String landmark;
	private String description;
	private String lineNum;
	private String lineStation;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLineStation() {
		return lineStation;
	}
	public void setLineStation(String lineStation) {
		this.lineStation = lineStation;
	}
	@Override
	public String toString() {
		return "RoomQueryBean [userId=" + userId + ", useName=" + useName
				+ ", gender=" + gender + ", phone=" + phone + ", minPrice="
				+ minPrice + ", maxPrice=" + maxPrice + ", province="
				+ province + ", city=" + city + ", area=" + area
				+ ", landmark=" + landmark + ", description=" + description
				+ ", lineNum=" + lineNum + ", lineStation=" + lineStation + "]";
	}
	
	
	
	
	
}
