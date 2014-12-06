package net.wecash.server.analyzer;

public enum TriggerType {
	FIRST_ANALYZER(1),
	RE_ANALYZER(2);
	private int type;
	private TriggerType(int type){
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
