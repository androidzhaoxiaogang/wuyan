package net.wecash.server.bean;

public class LandmarkAllBean {

	private String pcode;
	private String pname;
	
	private String ccode;
	private String cname;
	
	private String acode;
	private String aname;
	
	private String lcode;
	private String lname;
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCcode() {
		return ccode;
	}
	public void setCcode(String ccode) {
		this.ccode = ccode;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getAcode() {
		return acode;
	}
	public void setAcode(String acode) {
		this.acode = acode;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getLcode() {
		return lcode;
	}
	public void setLcode(String lcode) {
		this.lcode = lcode;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	@Override
	public String toString() {
		return "LandmarkAllBean [pcode=" + pcode + ", pname=" + pname
				+ ", ccode=" + ccode + ", cname=" + cname + ", acode=" + acode
				+ ", aname=" + aname + ", lcode=" + lcode + ", lname=" + lname
				+ "]";
	}
	
	
}
