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


@Entity
@Table(name=Collections.T_LEAVE_MESSAGE)
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5711468976247032927L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	@Column(name="from_user_id")
	private Long fromUserId;
	@Column(name="to_user_id")
	private Long toUserId;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time", updatable=false)
	private Date time;
	private Integer state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "LeaveMeaaage [id=" + id + ", fromUserId=" + fromUserId
				+ ", toUserId=" + toUserId + ", content=" + content + ", time="
				+ time + ", state=" + state + "]";
	}
	
}
