package net.wecash.server.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.wecash.common.service.Collections;

@Entity  
@Table( name = Collections.T_USER_BEHAVIOR )
public class BehaviorStatisticsInfo {
	@Id  
    @GeneratedValue(generator="increment")  
    @GenericGenerator(name="increment", strategy = "increment")  
	private Long id; 
	
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 总浏览的匹配结果个数
     */
    @Column(name = "total_match_count")
    private Long totalMatchCount;
    
    /**
     * 总筛选次数
     */
    @Column(name = "total_tilter_count")
    private Long totalFilterCount;
    
    /**
     * 当前浏览的匹配结果次数
     */
    @Column(name = "current_match_count")
    private Integer currentMatchCount;

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

	public Long getTotalMatchCount() {
		return totalMatchCount;
	}

	public void setTotalMatchCount(Long totalMatchCount) {
		this.totalMatchCount = totalMatchCount;
	}

	public Long getTotalFilterCount() {
		return totalFilterCount;
	}

	public void setTotalFilterCount(Long totalFilterCount) {
		this.totalFilterCount = totalFilterCount;
	}

	public Integer getCurrentMatchCount() {
		return currentMatchCount;
	}

	public void setCurrentMatchCount(Integer currentMatchCount) {
		this.currentMatchCount = currentMatchCount;
	}

	@Override
	public String toString() {
		return "BehaviorStatisticsInfo ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (userId != null ? "userId=" + userId + ", " : "")
				+ (totalMatchCount != null ? "totalMatchCount="
						+ totalMatchCount + ", " : "")
				+ (totalFilterCount != null ? "totalFilterCount="
						+ totalFilterCount + ", " : "")
				+ (currentMatchCount != null ? "currentMatchCount="
						+ currentMatchCount : "") + "]";
	}
    
}
