package jk.kamoru.crazy.domain;

import java.util.regex.Pattern;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.util.VideoUtils;
import jk.kamoru.util.StringUtils;
import lombok.Data;

@Data
public class TitlePart implements Comparable<TitlePart> {
	String studio;
	String opus;
	String title;
	String actress;
	String releaseDate;

	Boolean check;
	String checkDesc;
	
	final String regexKorean = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
	final String regexSimple = "\\d{4}.\\d{2}.\\d{2}";
	final String regexDate = "^((19|20)\\d\\d).(0?[1-9]|1[012]).(0?[1-9]|[12][0-9]|3[01])$";
	
	public TitlePart() {
		this.checkDesc = "";
	}
	
	public TitlePart(String title) {
		String[] parts = StringUtils.split(title, "]");
		if (parts != null)
			for (int i = 0; i < parts.length; i++) {
				setDate(i, VideoUtils.removeUnnecessaryCharacter(parts[i]));
			}
		else
			throw new CrazyException(String.format("parsing error : %s", title));
	}

	private void setDate(int i, String data) {
		switch (i) {
		case 0:
			studio = data;
			break;
		case 1:
			opus = data.toUpperCase();
			break;
		case 2:
			title = data;
			break;
		case 3:
			actress = data;
			break;
		case 4:
			releaseDate = data;
			break;
		default:
			throw new CrazyException(String.format("invalid title data. %s : %s", i, data));
		}
	}

	/**
	 * @param studio the studio to set
	 */
	public void setStudio(String studio) {
		this.studio = studio;
	}

	/**
	 * @param opus the opus to set
	 */
	public void setOpus(String opus) {
		this.opus = opus;
		// 공백이 포함되어 있으면
		if (StringUtils.containsWhitespace(opus)) {
			this.check = true;
			this.checkDesc += "Opus ";
		}
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		// 값이 없으면
		if (StringUtils.isBlank(title)) {
			this.check = true;
			this.checkDesc += "Title ";
		}
	}

	/**
	 * @param actress the actress to set
	 */
	public void setActress(String actress) {
		this.actress = actress;
		// 한글이 포함되어 있으면
		if (Pattern.matches(regexKorean, actress)) {
			this.check = true;
			this.checkDesc += "Actress ";
		}
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
		// 값이 없으면
		if (StringUtils.isBlank(releaseDate)) {
			this.check = true;
			this.checkDesc += "Date ";
		}
		else {
			// 패턴이 틀리면 
			if (!Pattern.matches(regexSimple, releaseDate)) {
				this.check = true;
				this.checkDesc += "Date ";
			}
			else if (!Pattern.matches(regexDate, releaseDate)) {
				this.check = true;
				this.checkDesc += "Date ";
			}
		}
	}

	@Override
	public String toString() {
		return String.format("[%s][%s][%s][%s][%s]", 
				studio, opus, title, actress, releaseDate);
	}

	@Override
	public int compareTo(TitlePart o) {
		return StringUtils.compareTo(this.toString(), o.toString());
	}

	public void setSeen() {
		this.check = true;
		this.checkDesc += "Seen ";
	}
}