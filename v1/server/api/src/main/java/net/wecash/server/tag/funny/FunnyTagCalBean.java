/**
 * 
 */
package net.wecash.server.tag.funny;

import java.util.List;

/**
 * @author franklin.li
 * 
 */
public class FunnyTagCalBean {
	private Integer habit;
	private String landmark;
	private Float personality;
	private Integer occupation;
	private List<Integer> interestList;
	
	public FunnyTagCalBean(Integer habit, String landmark, Float personality,
			Integer occupation, List<Integer> interestList) {
		super();
		this.habit = habit;
		this.landmark = landmark;
		this.personality = personality;
		this.occupation = occupation;
		this.interestList = interestList;
	}

	public Integer getHabit() {
		return habit;
	}

	public void setHabit(Integer habit) {
		this.habit = habit;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public Float getPersonality() {
		return personality;
	}

	public void setPersonality(Float personality) {
		this.personality = personality;
	}

	public Integer getOccupation() {
		return occupation;
	}

	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}

	public List<Integer> getInterestList() {
		return interestList;
	}

	public void setInterestList(List<Integer> interestList) {
		this.interestList = interestList;
	}

	@Override
	public String toString() {
		return "FunnyTagCalBean [habit=" + habit + ", landmark=" + landmark
				+ ", personality=" + personality + ", occupation=" + occupation
				+ ", interestList=" + interestList + "]";
	}
	
}
