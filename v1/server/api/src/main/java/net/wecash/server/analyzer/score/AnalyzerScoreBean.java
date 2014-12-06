package net.wecash.server.analyzer.score;

public class AnalyzerScoreBean {
	private Integer habit;//40%
	private Integer birthday;//30%
	private Integer interest;//20%
	private Integer socialRelation;//10%
	private Integer degree;
	private Integer occupation;
	private Integer total;
	private Long selfId;
	private Long targetId;
	private boolean flag;
	
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Long getSelfId() {
		return selfId;
	}
	public void setSelfId(Long selfId) {
		this.selfId = selfId;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Integer getHabit() {
		return habit;
	}
	public void setHabit(Integer habit) {
		this.habit = habit;
	}
	public Integer getBirthday() {
		return birthday;
	}
	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}
	public Integer getInterest() {
		return interest;
	}
	public void setInterest(Integer interest) {
		this.interest = interest;
	}
	public Integer getSocialRelation() {
		return socialRelation;
	}
	public void setSocialRelation(Integer socialRelation) {
		this.socialRelation = socialRelation;
	}
	@Override
	public String toString() {
		return "AnalyzerScoreBean [habit=" + habit + ", birthday=" + birthday
				+ ", interest=" + interest + ", socialRelation="
				+ socialRelation + ", degree=" + degree + ", occupation="
				+ occupation + ", total=" + total + ", selfId=" + selfId
				+ ", targetId=" + targetId + ", flag=" + flag + "]";
	}
	
}
