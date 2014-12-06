package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.wecash.common.service.Collections;

@Entity
@Table(name=Collections.T_AREA)
public class Area implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7116267414440868702L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String code;
	private String name;
	private String citycode;
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
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	@Override
	public String toString() {
		return "Area [id=" + id + ", code=" + code + ", name=" + name
				+ ", citycode=" + citycode + "]";
	}
	
}
