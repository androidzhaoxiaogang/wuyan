package net.wecash.server.bean;

public class UserContactRequestBean {

	private String Name;
	private Character gender;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "UserContactRequestBean [Name=" + Name + ", gender=" + gender
				+ "]";
	}
	
	
}
