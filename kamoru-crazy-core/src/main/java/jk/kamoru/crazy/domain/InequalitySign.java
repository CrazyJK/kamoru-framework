package jk.kamoru.crazy.domain;

public enum InequalitySign {
	
	eq("Equal"), lt("Less"), gt("Greater");

	private String desc;
	
	InequalitySign(String desc) {
		this.setDesc(desc);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
