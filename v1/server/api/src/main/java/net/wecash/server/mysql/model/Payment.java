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

import org.hibernate.annotations.GenericGenerator;

import net.wecash.common.service.Collections;

@Entity
@Table(name = Collections.T_PAYMENT)
public class Payment implements Serializable{

	private static final long serialVersionUID = -8455442838317971939L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private Integer type;
	private String desc;
	private Float[] factor;
	private Float[] remain;
	private Integer state;
	@Column(name = "user_id")
	private Integer userId;
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "create_time")
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Float[] getFactor() {
		return factor;
	}
	public void setFactor(Float[] factor) {
		this.factor = factor;
	}
	public Float[] getRemain() {
		return remain;
	}
	public void setRemain(Float[] remain) {
		this.remain = remain;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", type=" + type + ", desc=" + desc
				+ ", factor=" + factor + ", remain=" + remain + ", state="
				+ state + ", userId=" + userId + ", createTime=" + createTime
				+ "]";
	}
}
