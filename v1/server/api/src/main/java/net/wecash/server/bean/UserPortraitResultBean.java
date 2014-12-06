package net.wecash.server.bean;

import java.util.Date;

public class UserPortraitResultBean {

	private Integer userId;
	private Date birthday;
	private Float personality;
	private Integer habit;
	private Integer degree;
	private Integer occupation;
	private Integer type;
	private Integer state;
	private Float minPrice;
	private Float maxPrice;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Float minPrice) {
		this.minPrice = minPrice;
	}
	public Float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
	}
	@Override
	public String toString() {
		return "UserPortraitResultBean [userId=" + userId + ", birthday="
				+ birthday + ", personality=" + personality + ", habit="
				+ habit + ", degree=" + degree + ", occupation=" + occupation
				+ ", type=" + type + ", state=" + state + ", minPrice="
				+ minPrice + ", maxPrice=" + maxPrice + "]";
	}

}
