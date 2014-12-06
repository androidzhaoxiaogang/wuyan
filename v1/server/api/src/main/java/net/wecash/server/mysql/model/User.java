package net.wecash.server.mysql.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.wecash.common.service.Collections;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author xkk
 * 
 */
@Entity
@Table(name = Collections.T_USER)
public class User implements Serializable {

	private static final long serialVersionUID = -921629866889932238L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(length = 40)
	private String name;

	@Column(length = 250)
	private String description;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String gender;

	/**
	 * 用户类型 0管理员 1普通用户
	 */
	private Integer type;

	/**
	 * 0无需求 1 找室友 2 找房
	 */
	private Integer state;

	@Column(length = 20)
	private String phone;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", updatable = false)
	private Date createTime;

	/**
	 * 性格
	 */
	private Float personality;
	/**
	 * 习惯
	 */
	private Integer habit;
	/**
	 * 学历
	 */
	private Integer degree;
	/**
	 * 职业
	 */
	private Integer occupation;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		return "User [id=" + id + ", name=" + name + ", description="
				+ description + ", birthday=" + birthday + ", gender=" + gender
				+ ", type=" + type + ", state=" + state + ", phone=" + phone
				+ ", createTime=" + createTime + ", personality=" + personality
				+ ", habit=" + habit + ", degree=" + degree + ", occupation="
				+ occupation + "]";
	}

}
