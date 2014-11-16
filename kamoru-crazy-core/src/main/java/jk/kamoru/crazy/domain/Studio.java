package jk.kamoru.crazy.domain;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import jk.kamoru.crazy.CRAZY;
import jk.kamoru.crazy.util.VideoUtils;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;
import lombok.Data;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@XmlRootElement(name = "studio", namespace = "http://www.w3.org/2001/XMLSchema-instance")
@XmlAccessorType(XmlAccessType.FIELD)
public class Studio implements Serializable, Comparable<Studio> {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	private static final String NAME 	 = "NAME";
	private static final String COMPANY  = "COMPANY";
	private static final String HOMEPAGE = "HOMEPAGE";
	
    private String[] videoStoragePaths;

	private String name;
	private URL    homepage;
	private String company;

	@XmlTransient
	@JsonIgnore
	private List<Video> videoList;
	@XmlTransient
	@JsonIgnore
	private List<Actress> actressList;

	private SortStudio sort = SortStudio.NAME;
	
	public Studio() {
		videoList = new ArrayList<Video>();
		actressList = new ArrayList<Actress>();
	}

	public Studio(String name) {
		this();
		this.name = name;
		loadInfo();
	}
	
	public void addVideo(Video video) {
		if(!videoList.contains(video))
			videoList.add(video);		
	}
	
	public void addActress(Actress newActress) {
		boolean found = false;
		for(Actress actress : this.actressList) {
			if(actress.getName().equalsIgnoreCase(newActress.getName())) {
				actress = newActress;
				found = true;
				break;
			}
		}
		if(!found)
			actressList.add(newActress);
	}

	/**
	 * sum of video scoring in studio
	 * @return
	 */
	public int getScore() {
		int score = 0;
		for (Video video : getVideoList())
			score += video.getScore();
		return score;
	}

	/**
	 * 변경된 정보 업데이트. company, 
	 * @param studio
	 */
	public void merge(Studio studio) {
		this.setName(studio.getName());
		this.setCompany(studio.getCompany());
		this.setHomepage(studio.getHomepage());
		saveInfo();
	}

	public void loadInfo() {
		Map<String, String> info = VideoUtils.readFileToMap(getInfoFile());
		try {
			this.homepage = new URL(info.get(HOMEPAGE));
		} catch (MalformedURLException e) {
			// do nothing!
		}
		this.company = info.get(COMPANY);
	}

	private void saveInfo() {
		Map<String, String> info = new HashMap<String, String>();
		info.put(NAME, name);
		info.put(COMPANY, company);
		info.put(HOMEPAGE, homepage.toString());

		VideoUtils.saveFileFromMap(getInfoFile(), info);
	}
	
	private File getInfoFile() {
		return new File(videoStoragePaths[0], getName() + FileUtils.EXTENSION_SEPARATOR + CRAZY.EXT_STUDIO);
	}
	
	@Override
	public int compareTo(Studio comp) {
		switch (sort) {
		case NAME:
			return StringUtils.compareToIgnoreCase(this.getName(), comp.getName());
		case HOMEPAGE:
			return StringUtils.compareTo(this.getHomepage(), comp.getHomepage());
		case COMPANY:
			return StringUtils.compareToIgnoreCase(this.getCompany(), comp.getCompany());
		case VIDEO:
			return this.getVideoList().size() - comp.getVideoList().size();
		case SCORE:
			return this.getScore() - comp.getScore();
		default:
			return StringUtils.compareToIgnoreCase(this.getName(), comp.getName());
		}
	}

	@Override
	public String toString() {
		return String.format("%s Score %s %s %s",
				name, getScore(), StringUtils.trimToEmpty(homepage), StringUtils.trimToEmpty(company));
	}

	public void emptyVideo() {
		videoList.clear();
	}

}
