package net.wecash.server.bean;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdateBean {
	@Size(max=20)
	private String name;
	@Pattern(regexp="[m|f|s]")
	private String gender;
	private Date birthday;
	private String phone;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	@Override
	public String toString() {
		return "UserUpdateBean [name=" + name + ", gender=" + gender
				+ ", birthday=" + birthday + ", phone=" + phone
				+ ", description=" + description + ", state=" + state
				+ ", personality=" + personality + ", habit=" + habit
				+ ", degree=" + degree + ", occupation=" + occupation + "]";
	}

}
