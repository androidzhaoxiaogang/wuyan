package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.wecash.common.service.Collections;

@Entity
@Table(name = Collections.T_USER_IMG)
public class UserImg implements Serializable{
	private static final long serialVersionUID = -6495082443324836459L;
	@Id  
    @GeneratedValue(generator="increment")  
    @GenericGenerator(name="increment", strategy = "increment")  
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	private String image;
	private Integer type;
	
	public UserImg(){}
	public UserImg(Long userId, String image, Integer type) {
		super();
		this.userId = userId;
		this.image = image;
		this.type = type;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "UserImg [id=" + id + ", userId=" + userId + ", image=" + image
				+ ", type=" + type + "]";
	}
	
	
	
}
