package net.wecash.data.sns.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author franklin.li
 * 
 */
public class SnsDataBean {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String uid;
	private Integer type;
	private Long userId;
	private String token;
	private String name;
	private List<String> tags;
	private List<String> timelineSubjectTerm;
	private List<Map> bilateralFriends;
	private String profileImage;
	private Date createTime;
	
	
	public SnsDataBean(String uid, Long userId, String token, Integer type, String name,
			List<String> tags, List<String> timelineSubjectTerm,
			List<Map> bilateralFriends, String profileImage) {
		super();
		this.uid = uid;
		this.userId = userId;
		this.token = token;
		this.type = type;
		this.name = name;
		this.tags = tags;
		this.timelineSubjectTerm = timelineSubjectTerm;
		this.bilateralFriends = bilateralFriends;
		this.profileImage = profileImage;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getTimelineSubjectTerm() {
		return timelineSubjectTerm;
	}
	public void setTimelineSubjectTerm(List<String> timelineSubjectTerm) {
		this.timelineSubjectTerm = timelineSubjectTerm;
	}
	public List<Map> getBilateralFriends() {
		return bilateralFriends;
	}
	public void setBilateralFriends(List<Map> bilateralFriends) {
		this.bilateralFriends = bilateralFriends;
	}
	@Override
	public String toString() {
		return "SnsDataBean [id=" + id + ", uid=" + uid + ", userId=" + userId
				+ ", token=" + token + ", name=" + name + ", tags=" + tags
				+ ", timelineSubjectTerm=" + timelineSubjectTerm
				+ ", bilateralFriends=" + bilateralFriends + ", profileImage="
				+ profileImage + ", createTime=" + createTime + "]";
	}
	
}
