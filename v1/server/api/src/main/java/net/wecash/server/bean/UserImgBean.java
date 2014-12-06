/**
 * 
 */
package net.wecash.server.bean;

/**
 * @author franklin.li
 * 
 */
public class UserImgBean {
	private Integer userId;
	private String image;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "UserImgBean [userId=" + userId + ", image=" + image + "]";
	}
	
}
