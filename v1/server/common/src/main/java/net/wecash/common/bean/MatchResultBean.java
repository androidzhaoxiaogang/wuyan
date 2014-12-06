/**
 * 
 */
package net.wecash.common.bean;

/**
 * @author franklin.li
 * 
 */
public class MatchResultBean {
	private Object result;
	private Float infoIntegrityLevel;
	private Long total;
	
	public MatchResultBean(Object result, Float infoIntegrityLevel) {
		super();
		this.result = result;
		this.infoIntegrityLevel = infoIntegrityLevel;
	}
	
	public MatchResultBean(Object result, Float infoIntegrityLevel, Long total) {
		super();
		this.result = result;
		this.infoIntegrityLevel = infoIntegrityLevel;
		this.total = total;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Float getInfoIntegrityLevel() {
		return infoIntegrityLevel;
	}
	public void setInfoIntegrityLevel(Float infoIntegrityLevel) {
		this.infoIntegrityLevel = infoIntegrityLevel;
	}
	@Override
	public String toString() {
		return "StateResultBean [result=" + result + ", infoIntegrityLevel="
				+ infoIntegrityLevel + "]";
	}
}
