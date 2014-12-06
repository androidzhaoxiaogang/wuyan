package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.wecash.common.service.Collections;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name=Collections.T_LANDMARK)
public class LandMark implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6378329097038932234L;
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String code;
	private String name;
	private String areacode;
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
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	@Override
	public String toString() {
		return "LandMark [id=" + id + ", code=" + code + ", name=" + name
				+ ", areacode=" + areacode + "]";
	}
	
}
