package net.wecash.server.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.wecash.common.service.Collections;

@Entity  
@Table( name = Collections.T_USER_SOCIAL_AUTH_INFO )
public class SnsAuthInfo {
	@Id  
    @GeneratedValue(generator="increment")  
    @GenericGenerator(name="increment", strategy = "increment")  
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="third_id")
	private String thirdId;
	
	@Column(name="third_token")
	private String thirdToken;
	
	private Integer type;

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

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getThirdToken() {
		return thirdToken;
	}

	public void setThirdToken(String thirdToken) {
		this.thirdToken = thirdToken;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SnsAuthInfo [" + (id != null ? "id=" + id + ", " : "")
				+ (userId != null ? "userId=" + userId + ", " : "")
				+ (thirdId != null ? "thirdId=" + thirdId + ", " : "")
				+ (thirdToken != null ? "thirdToken=" + thirdToken + ", " : "")
				+ (type != null ? "type=" + type : "") + "]";
	}

}
