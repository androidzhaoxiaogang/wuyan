package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.wecash.common.service.Collections;


@Entity
@Table(name=Collections.T_CITY)
public class City implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2249921951686315502L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String code;
	private String name;
	private String provincecode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvincecode() {
		return provincecode;
	}
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	@Override
	public String toString() {
		return "City [id=" + id + ", code=" + code + ", name=" + name
				+ ", provincecode=" + provincecode + "]";
	}
	
}
