package net.wecash.common.mongo.model;

import java.io.Serializable;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author xkk
 *
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5273377005832379345L;
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String username;
	private String nickname;
	private String phone;
	private String password;
	private String name;
	private String sex;
	
	private Float[] location;
	private String roomInfo;
	private Integer price;
	
	private Long birthday;
	/**
	 * 兴趣
	 */
	private String[] interest;
	/**
	 * 性格
	 */
	private String character;
	/**
	 * 习惯
	 */
	private String habit;
	/**
	 * 学历
	 */
	private String degree;
	/**
	 * 职业
	 */
	private String occupation;
	private String[] funnyTag;
	/**
	 * 用户权重
	 */
	private Integer weight;
	/**
	 * 用户等级
	 */
	private Integer grade;
	/**
	 * 用户角色类型 (0 管理员 1用户)
	 */
	private Integer roleType;
	/**
	 * 用户登录ip地址
	 */
	private String ip;
	private Long createTime;
	/**
	 * 登陆次数
	 */
	private Integer login;
	/**
	 * 登录时间
	 */
	private Long LoginTime;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Float[] getLocation() {
		return location;
	}
	public void setLocation(Float[] location) {
		this.location = location;
	}
	public String getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public String[] getInterest() {
		return interest;
	}
	public void setInterest(String[] interest) {
		this.interest = interest;
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String[] getFunnyTag() {
		return funnyTag;
	}
	public void setFunnyTag(String[] funnyTag) {
		this.funnyTag = funnyTag;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getLogin() {
		return login;
	}
	public void setLogin(Integer login) {
		this.login = login;
	}
	public Long getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(Long loginTime) {
		LoginTime = loginTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "User ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (nickname != null ? "nickname=" + nickname + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (sex != null ? "sex=" + sex + ", " : "")
				+ (location != null ? "location=" + Arrays.toString(location)
						+ ", " : "")
				+ (roomInfo != null ? "roomInfo=" + roomInfo + ", " : "")
				+ (price != null ? "price=" + price + ", " : "")
				+ (birthday != null ? "birthday=" + birthday + ", " : "")
				+ (interest != null ? "interest=" + Arrays.toString(interest)
						+ ", " : "")
				+ (character != null ? "character=" + character + ", " : "")
				+ (habit != null ? "habit=" + habit + ", " : "")
				+ (degree != null ? "degree=" + degree + ", " : "")
				+ (occupation != null ? "occupation=" + occupation + ", " : "")
				+ (funnyTag != null ? "funnyTag=" + Arrays.toString(funnyTag)
						+ ", " : "")
				+ (weight != null ? "weight=" + weight + ", " : "")
				+ (grade != null ? "grade=" + grade + ", " : "")
				+ (roleType != null ? "roleType=" + roleType + ", " : "")
				+ (ip != null ? "ip=" + ip + ", " : "")
				+ (createTime != null ? "createTime=" + createTime + ", " : "")
				+ (login != null ? "login=" + login + ", " : "")
				+ (LoginTime != null ? "LoginTime=" + LoginTime : "") + "]";
	}
	
}
