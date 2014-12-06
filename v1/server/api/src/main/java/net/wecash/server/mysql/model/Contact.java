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
@Table(name = Collections.T_CONTACT)
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5432988941880857150L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	@Column(name = "from_user_id")
	private Long fromUserId;
	@Column(name = "to_user_id")
	private Long toUserId;
	private Long permit;
	

	public Long getPermit() {
		return permit;
	}

	public void setPermit(Long permit) {
		this.permit = permit;
	}

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

	@Override
	public String toString() {
		return "Contact [id=" + id + ", fromUserId=" + fromUserId
				+ ", toUserId=" + toUserId + ", permit=" + permit + "]";
	}


}
