package net.wecash.server.mysql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.wecash.common.service.Collections;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name=Collections.T_SUBWAY)
public class Subway implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6345436512596503097L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private String citycode;
	@Column(name = "line_num")
	private String lineNum;
	@Column(name = "line_station")
	private String lineStation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	public String getLineStation() {
		return lineStation;
	}

	public void setLineStation(String lineStation) {
		this.lineStation = lineStation;
	}

	@Override
	public String toString() {
		return "Subway [id=" + id + ", citycode=" + citycode + ", lineNum="
				+ lineNum + ", lineStation=" + lineStation + "]";
	}

}
