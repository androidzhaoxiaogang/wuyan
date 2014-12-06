package net.wecash.server.analyzer.bean;

import java.util.List;

public class ScoreTriggerBean {
	private Long userId;
	private List<Long> targetUserIds;
	private List<Long> favorites;
	
	public ScoreTriggerBean(Long userId, List<Long> targetUserIds,
			List<Long> favorites) {
		super();
		this.userId = userId;
		this.targetUserIds = targetUserIds;
		this.favorites = favorites;
	}
	public List<Long> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<Long> favorites) {
		this.favorites = favorites;
	}
	public ScoreTriggerBean(Long userId, List<Long> targetUserIds) {
		super();
		this.userId = userId;
		this.targetUserIds = targetUserIds;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Long> getTargetUserIds() {
		return targetUserIds;
	}
	public void setTargetUserIds(List<Long> targetUserIds) {
		this.targetUserIds = targetUserIds;
	}
	@Override
	public String toString() {
		return "AnalyzerTriggerBean [userId=" + userId + ", targetUserIds="
				+ targetUserIds + "]";
	}
	
}