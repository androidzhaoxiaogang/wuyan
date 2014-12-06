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
@Table(name=Collections.T_ACTIVITY)
public class Activity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String cover;
	private String details;
	private String describes;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", updatable=false)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_time")
	private Date expireTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", cover=" + cover + ", details="
				+ details + ", describes=" + describes + ", createTime="
				+ createTime + ", expireTime=" + expireTime + "]";
	}
	
	
	
	
}
