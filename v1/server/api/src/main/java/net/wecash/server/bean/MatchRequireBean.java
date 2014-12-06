package net.wecash.server.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MatchRequireBean {

	private Integer userId;
	private String name;
	private Character gender;
	private Date birthday;
	private Float personality;
	private Integer minPrice;
	private Integer maxPrice;
	private String province;
	private String city;
	private String area;
	private String landmark;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Float getPersonality() {
		return personality;
	}
	public void setPersonality(Float personality) {
		this.personality = personality;
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
		return "MatchRequireBean [userId=" + userId + ", name=" + name
				+ ", gender=" + gender + ", birthday=" + birthday
				+ ", personality=" + personality + ", minPrice=" + minPrice
				+ ", maxPrice=" + maxPrice + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", landmark="
				+ landmark + "]";
	}
	
}
