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
@Table(name = Collections.T_ROOM_INFO)
public class RoomInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3558998746591601674L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	@Column(name="min_price")
	private Integer minPrice;
	@Column(name="max_price")
	private Integer maxPrice;
	private Float lat;
	private Float lng;
	private String description;
	@Column(name = "user_id")
	private Long userId;
	private String province;
	private String city;
	private String area;
	private String landmark;
	@Column(name = "line_num")
	private String lineNum;
	@Column(name = "line_station")
	private String lineStation;

	
	public RoomInfo() {
		super();
	}

	public RoomInfo(Integer minPrice, Integer maxPrice, Long userId,
			String province, String city, String area, String landmark) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.userId = userId;
		this.province = province;
		this.city = city;
		this.area = area;
		this.landmark = landmark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
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
		return "RoomInfo [id=" + id + ", minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", lat=" + lat + ", lng=" + lng + ", description="
				+ description + ", userId=" + userId + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", landmark="
				+ landmark + ", lineNum=" + lineNum + ", lineStation="
				+ lineStation + "]";
	}

	

}
