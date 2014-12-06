package net.wecash.data.analyzer.interest.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterestEncodeBean {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private Long userId;
	private List<Integer> interest;
	private String interestEncode;
	private Date timestamp;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getInterestEncode() {
		return interestEncode;
	}
	public void setInterestEncode(String interestEncode) {
		this.interestEncode = interestEncode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public List<Integer> getInterest() {
		return interest;
	}
	public void setInterest(List<Integer> interest) {
		this.interest = interest;
	}
	@Override
	public String toString() {
		return "InterestEncodeBean [id=" + id + ", userId=" + userId
				+ ", interest=" + interest + ", interestEncode="
				+ interestEncode + "]";
	}
}
