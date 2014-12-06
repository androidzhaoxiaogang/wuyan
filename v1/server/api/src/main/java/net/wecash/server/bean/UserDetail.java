package net.wecash.server.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class UserDetail {

	private Integer userId;
	private String name;
	private Character gender;
	private Date birthday;
	private Integer age;
	private String constellation;
	private Float personality;
	private Integer habit;
	private Integer degree;
	private Integer occupation;
	private String tagId;
	private Integer type;
	private Integer state;
	private List<String> image;
	private String icon;
	private Integer minPrice;
	private Integer maxPrice;
	private String province;
	private String city;
	private String area;
	private String landmark;
	private String place;
	private String description;
	private List<Map> friends;
	private List<String> tags;
	private Map score;
	private Boolean isFavorite;
	
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Boolean getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
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
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
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
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
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
	public List<Map> getFriends() {
		return friends;
	}
	public void setFriends(List<Map> friends) {
		this.friends = friends;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Map getScore() {
		return score;
	}
	public void setScore(Map score) {
		this.score = score;
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
	@Override
	public String toString() {
		return "UserDetail [userId=" + userId + ", name=" + name + ", gender="
				+ gender + ", birthday=" + birthday + ", age=" + age
				+ ", constellation=" + constellation + ", personality="
				+ personality + ", habit=" + habit + ", degree=" + degree
				+ ", occupation=" + occupation + ", tagId=" + tagId + ", type="
				+ type + ", state=" + state + ", image=" + image + ", icon="
				+ icon + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
				+ ", province=" + province + ", city=" + city + ", area="
				+ area + ", landmark=" + landmark + ", place=" + place
				+ ", description=" + description + ", friends=" + friends
				+ ", tags=" + tags + ", score=" + score + ", isFavorite="
				+ isFavorite + "]";
	}
	
}
