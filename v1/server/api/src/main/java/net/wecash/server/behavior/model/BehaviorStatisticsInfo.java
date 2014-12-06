package net.wecash.server.behavior.model;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BehaviorStatisticsInfo {
	@Id
	@JsonProperty("_id")
	private ObjectId id; 
	
    private Long userId;
    
    /**
     * 总浏览的匹配结果个数
     */
    private Long totalMatchCount;
    
    /**
     * 总筛选次数
     */
    private Long totalFilterCount;
    
    private Date lastMatchTime;

	public Date getLastMatchTime() {
		return lastMatchTime;
	}

	public void setLastMatchTime(Date lastMatchTime) {
		this.lastMatchTime = lastMatchTime;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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

	@Override
	public String toString() {
		return "BehaviorStatisticsInfo [id=" + id + ", userId=" + userId
				+ ", totalMatchCount=" + totalMatchCount
				+ ", totalFilterCount=" + totalFilterCount + ", lastMatchTime="
				+ lastMatchTime + "]";
	}

}
