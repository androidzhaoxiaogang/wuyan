package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.wecash.common.service.Collections;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name=Collections.R_USER_TAG)
public class UserTag implements Serializable{

	private static final long serialVersionUID = 6333364557552131884L;
	
	@Id  
    @GeneratedValue(generator="increment")  
    @GenericGenerator(name="increment", strategy = "increment")  
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="tag_id")
	private Long tagId;

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

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	@Override
	public String toString() {
		return "UserTag [id=" + id + ", userId=" + userId + ", tagId=" + tagId
				+ "]";
	}
	
	
}
