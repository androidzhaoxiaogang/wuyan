package net.wecash.common.bean;

public class BasicResultBean {
	private long total;
	private int cursor;
	private int limit;
	private Object result;

	public BasicResultBean(long total, int cursor, int limit, Object result) {
		this.total = total;
		this.cursor = cursor;
		this.limit = limit;
		this.result = result;
	}

	public int getCursor() {
		return cursor;
	}

	public int getLimit() {
		return limit;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	 public Object getResult() {
		return result;
	}

	 public void setResult(Object result) {
		 this.result = result;
	 }

	 public long getTotal() {
		 return total;
	 }

	 public void setTotal(long total) {
		 this.total = total;
	 }
}
