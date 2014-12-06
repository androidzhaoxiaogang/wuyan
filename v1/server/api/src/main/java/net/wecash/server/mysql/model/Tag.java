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
@Table( name = Collections.T_TAG )
public class Tag implements Serializable{
	private static final long serialVersionUID = -946679021741294930L;

	@Id  
    @GeneratedValue(generator="increment")  
    @GenericGenerator(name="increment", strategy = "increment")  
	private Long id;  
	
	@Column(length=15)
    private String name;
	
	private Integer weight;
	
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Tag [" + (id != null ? "id=" + id + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (weight != null ? "weight=" + weight + ", " : "")
				+ (type != null ? "type=" + type : "") + "]";
	}
	
}
