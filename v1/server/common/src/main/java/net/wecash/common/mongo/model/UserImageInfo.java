package net.wecash.common.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserImageInfo {
	@Id
	@JsonProperty("_id")
	private ObjectId id;
	private String username;
	
	private ObjectId user1;
	private ObjectId user2;
	private ObjectId user3;
	private ObjectId uScale1;
	private ObjectId uScale2;
	private ObjectId uScale3;
	
	private ObjectId room1;
	private ObjectId room2;
	private ObjectId room3;
	private ObjectId room4;
	private ObjectId room5;
	private ObjectId rScale1;
	private ObjectId rScale2;
	private ObjectId rScale3;
	private ObjectId rScale4;
	private ObjectId rScale5;
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
	public ObjectId getUser1() {
		return user1;
	}
	public void setUser1(ObjectId user1) {
		this.user1 = user1;
	}
	public ObjectId getUser2() {
		return user2;
	}
	public void setUser2(ObjectId user2) {
		this.user2 = user2;
	}
	public ObjectId getUser3() {
		return user3;
	}
	public void setUser3(ObjectId user3) {
		this.user3 = user3;
	}
	public ObjectId getuScale1() {
		return uScale1;
	}
	public void setuScale1(ObjectId uScale1) {
		this.uScale1 = uScale1;
	}
	public ObjectId getuScale2() {
		return uScale2;
	}
	public void setuScale2(ObjectId uScale2) {
		this.uScale2 = uScale2;
	}
	public ObjectId getuScale3() {
		return uScale3;
	}
	public void setuScale3(ObjectId uScale3) {
		this.uScale3 = uScale3;
	}
	public ObjectId getRoom1() {
		return room1;
	}
	public void setRoom1(ObjectId room1) {
		this.room1 = room1;
	}
	public ObjectId getRoom2() {
		return room2;
	}
	public void setRoom2(ObjectId room2) {
		this.room2 = room2;
	}
	public ObjectId getRoom3() {
		return room3;
	}
	public void setRoom3(ObjectId room3) {
		this.room3 = room3;
	}
	public ObjectId getRoom4() {
		return room4;
	}
	public void setRoom4(ObjectId room4) {
		this.room4 = room4;
	}
	public ObjectId getRoom5() {
		return room5;
	}
	public void setRoom5(ObjectId room5) {
		this.room5 = room5;
	}
	public ObjectId getrScale1() {
		return rScale1;
	}
	public void setrScale1(ObjectId rScale1) {
		this.rScale1 = rScale1;
	}
	public ObjectId getrScale2() {
		return rScale2;
	}
	public void setrScale2(ObjectId rScale2) {
		this.rScale2 = rScale2;
	}
	public ObjectId getrScale3() {
		return rScale3;
	}
	public void setrScale3(ObjectId rScale3) {
		this.rScale3 = rScale3;
	}
	public ObjectId getrScale4() {
		return rScale4;
	}
	public void setrScale4(ObjectId rScale4) {
		this.rScale4 = rScale4;
	}
	public ObjectId getrScale5() {
		return rScale5;
	}
	public void setrScale5(ObjectId rScale5) {
		this.rScale5 = rScale5;
	}
	@Override
	public String toString() {
		return "UserImageInfo [" + (id != null ? "id=" + id + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (user1 != null ? "user1=" + user1 + ", " : "")
				+ (user2 != null ? "user2=" + user2 + ", " : "")
				+ (user3 != null ? "user3=" + user3 + ", " : "")
				+ (uScale1 != null ? "uScale1=" + uScale1 + ", " : "")
				+ (uScale2 != null ? "uScale2=" + uScale2 + ", " : "")
				+ (uScale3 != null ? "uScale3=" + uScale3 + ", " : "")
				+ (room1 != null ? "room1=" + room1 + ", " : "")
				+ (room2 != null ? "room2=" + room2 + ", " : "")
				+ (room3 != null ? "room3=" + room3 + ", " : "")
				+ (room4 != null ? "room4=" + room4 + ", " : "")
				+ (room5 != null ? "room5=" + room5 + ", " : "")
				+ (rScale1 != null ? "rScale1=" + rScale1 + ", " : "")
				+ (rScale2 != null ? "rScale2=" + rScale2 + ", " : "")
				+ (rScale3 != null ? "rScale3=" + rScale3 + ", " : "")
				+ (rScale4 != null ? "rScale4=" + rScale4 + ", " : "")
				+ (rScale5 != null ? "rScale5=" + rScale5 : "") + "]";
	}

}
