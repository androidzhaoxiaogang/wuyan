package net.wecash.server.bean;


import java.util.List;

import javax.persistence.Entity;

@Entity
public class UserTagBean {

	private Long id;
	private Long userId;
	private List<Long> tagId;
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
	
	public List<Long> getTagId() {
		return tagId;
	}
	public void setTagId(List<Long> tagId) {
		this.tagId = tagId;
	}
	@Override
	public String toString() {
		return "UserTagBean [id=" + id + ", userId=" + userId + ", tagId="
				+ tagId + "]";
	}
	
}
