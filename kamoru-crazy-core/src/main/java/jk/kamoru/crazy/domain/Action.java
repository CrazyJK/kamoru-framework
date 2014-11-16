package jk.kamoru.crazy.domain;

/**Video action
 * @author kamoru
 */
public enum Action {
	/**
	 * play video
	 */
	PLAY("Play"), 
	/**
	 * save overview
	 */
	OVERVIEW("Overview"), 
	/**
	 * view cover
	 */
	COVER("Cover"), 
	/**
	 * edit subtitles
	 */
	SUBTITLES("Subtitles"), 
	/**
	 * delete video
	 */
	DELETE("Delete"),
	/**
	 * mark rank
	 */
	RANK("Rank");

	/**
	 * action description
	 */
	private String actionString;
	
	Action(String action) {
		this.actionString = action;
	}
	
	public String toString() {
		return actionString;
	}
}
