package jk.kamoru.crazy.domain;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jk.kamoru.crazy.CRAZY;
import lombok.Data;

@Data
public class History implements Serializable {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;
	
	private Date date;
	private String opus;
	private Action action;
	private String desc;
	private Video video;
	
	public History() {}
	
	public History(Video video, Action action) {
		this.video = video;
		this.action = action;
		this.opus = video.getOpus();
		this.date = new Date();
	}

	@Override
	public String toString() {
		return String.format("History [date=%s, opus=%s, action=%s, desc=%s]",
				date, opus, action, desc);
	}
	
	public String toFileSaveString() {
		String desc = null; 
		switch(action) {
			case PLAY :
			case OVERVIEW :
			case COVER :
			case SUBTITLES :
			case DELETE :
				desc = video.getFullname();
				break;
			case RANK :
				desc = String.valueOf(video.getRank()) + " - " + video.getFullname();
				break;
			default:
				desc = "Undefined Action : " + action.toString();
		}
		this.desc = desc;
		return MessageFormat.format("{0}, {1}, {2}, \"{3}\"{4}",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), 
				video.getOpus(), 
				action, 
				desc, 
				System.getProperty("line.separator"));
	}

}
