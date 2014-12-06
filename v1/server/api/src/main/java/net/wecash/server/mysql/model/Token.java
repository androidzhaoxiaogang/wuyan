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

/**
 * 
 * @author xkk
 *
 */
@Entity  
@Table( name = Collections.T_USER_TOKEN )
public class Token implements Serializable{
	
	private static final long serialVersionUID = 7892801916978764373L;
	@Id  
    @GeneratedValue(generator="increment")  
    @GenericGenerator(name="increment", strategy = "increment")  
	private Long id; 
	
	private String token;
	
	private Integer type;
	
	@Column(name="expires_in")
	private Long expiresIn;
	
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "create_time")
    private Date createTime;
    
//    @PrimaryKeyJoinColumn
    @Column(name = "user_id")
    private long userId;
    
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "client_secret")
    private String clientSecret;
    
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", token=" + token + ", type=" + type
				+ ", expiresIn=" + expiresIn + ", createTime=" + createTime
				+ ", userId=" + userId + ", clientId=" + clientId
				+ ", clientSecret=" + clientSecret + "]";
	}

}
