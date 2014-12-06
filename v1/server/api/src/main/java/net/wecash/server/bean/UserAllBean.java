/**
 * 
 */
package net.wecash.server.bean;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author franklin.li
 * 
 */
public class UserAllBean {
	@Size(max=20)
	private String name;
	@Pattern(regexp="[m|f|s]")
	private String gender;
	private Date birthday;
	@Size(max=160)
	private String description;
	@Max(2)
	@Min(0)
	private Integer state;
	@Max(1)
	@Min(0)
	private Float personality;
	@Max(3)
	@Min(0)
	private Integer habit;
	@Max(4)
	@Min(0)
	private Integer degree;
	@Max(6)
	@Min(0)
	private Integer occupation;
	private Integer minPrice;
	private Integer maxPrice;
	private String province;
	private String city;
	private String area;
	private String landmark;
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getPersonality() {
		return personality;
	}
	public void setPersonality(Float personality) {
		this.personality = personality;
	}
	public Integer getHabit() {
		return habit;
	}
	public void setHabit(Integer habit) {
		this.habit = habit;
	}
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
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
		return "UserAllBean [name=" + name + ", gender=" + gender
				+ ", birthday=" + birthday + ", description=" + description
				+ ", state=" + state + ", personality=" + personality
				+ ", habit=" + habit + ", degree=" + degree + ", occupation="
				+ occupation + ", minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", province=" + province + ", city=" + city
				+ ", area=" + area + ", landmark=" + landmark + "]";
	}
	
}
