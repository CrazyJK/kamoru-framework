package jk.kamoru.crazy.domain;

/**video sort
 * @author kamoru
 */
public enum SortVideo {

	/** studio sort */
	S("Studio"), 
	/** opus sort */
	O("Opus"), 
	/** title sort */
	T("Title"), 
	/** actress name sort */
	A("Actress"), 
	/** file modified date sort */
	M("Modified"), 
	/** play count sort */
	P("PlayCount"), 
	/** rank sort */
	R("Rank"), 
	/** video length sort */
	L("Length"), 
	/** score sort */
	SC("Score");
	
	private String sortString;
	
	SortVideo(String sort) {
		this.sortString = sort;
	}
	
	public String toString() {
		return sortString;
	}

	public String getDesc() {
		return sortString;
	}
}
