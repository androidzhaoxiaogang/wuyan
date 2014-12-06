package net.wecash.server.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class PaymentCreateBean {

	@NotNull
	private String desc;
	private float factor;
	private float remain;
	private Integer state;
	private Date createTime;
	private Integer type; 
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public float getFactor() {
		return factor;
	}
	public void setFactor(float factor) {
		this.factor = factor;
	}
	public float getRemain() {
		return remain;
	}
	public void setRemain(float remain) {
		this.remain = remain;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "PaymentBean [desc=" + desc + ", factor=" + factor + ", remain="
				+ remain + ", state=" + state + "]";
	}
	
}
