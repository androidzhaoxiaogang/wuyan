package net.wecash.server.bean;

import java.util.Date;

public class UserCollectBean {

	private Integer userId;
	private Character gender;
	private Date birthday;
	private String name;
	private String icon;
	private Integer minPrice;
	private Integer maxPrice;
	private String area;
	private String landmark;
	private String constellation;
	private Integer age;
	private Integer occupation;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "UserCollectBean [userId=" + userId + ", gender=" + gender
				+ ", birthday=" + birthday + ", name=" + name + ", icon="
				+ icon + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
				+ ", area=" + area + ", landmark=" + landmark
				+ ", constellation=" + constellation + ", age=" + age
				+ ", occupation=" + occupation + "]";
	}
	
	
	
	
}
