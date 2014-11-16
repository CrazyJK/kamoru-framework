package jk.kamoru.crazy.domain;

import java.io.File;
import java.io.Serializable;
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

@Scope("prototype")
@Data
@XmlRootElement(name = "actress", namespace = "http://www.w3.org/2001/XMLSchema-instance")
@XmlAccessorType(XmlAccessType.FIELD)
public class Actress implements Serializable, Comparable<Actress> {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	private static final String NAME 		= "NAME";
	private static final String LOCALNAME 	= "LOCALNAME";
	private static final String BIRTH 		= "BIRTH";
	private static final String HEIGHT 		= "HEIGHT";
	private static final String BODYSIZE 	= "BODYSIZE";
	private static final String DEBUT 		= "DEBUT";
	
    private String[] videoStoragePaths;

	private String name;
	private String localName;
	private String birth;
	private String bodySize;
	private String debut;
	private String height;
	
	@XmlTransient
	@JsonIgnore
	private List<Studio> studioList;
	@XmlTransient
	@JsonIgnore
	private List<Video>   videoList;
	
	private SortActress sort = SortActress.NAME;

	public Actress() {
		studioList = new ArrayList<Studio>();
		videoList = new ArrayList<Video>();
	}
	public Actress(String name) {
		this();
		this.name = name;
		loadInfo();
	}
	
	public boolean contains(String actressName) {
		return VideoUtils.equalsName(name, actressName);
	}
		
	public List<URL> getWebImage() {
		return VideoUtils.getGoogleImage(name);
	}
	

	public void addStudio(Studio studio) {
		if(!studioList.contains(studio))
			studioList.add(studio);
	}
	
	public void addVideo(Video video) {
		if(!videoList.contains(video))
			videoList.add(video);
	}
	
	public void emptyVideo() {
		videoList.clear();
	}
	
	/**
	 * sum of video scoring in actress
	 * @return
	 */
	public int getScore() {
		int score  = 0;
		for (Video video : getVideoList()) {
			score += video.getScore();
		}
		return score;
	}
	
	public void merge(Actress actress) {
		this.setBirth(actress.getBirth());
		this.setBodySize(actress.getBodySize());
		this.setDebut(actress.getDebut());
		this.setHeight(actress.getHeight());
		this.setLocalName(actress.getLocalName());
		this.setName(actress.getName());
		this.saveInfo();
	}
	
	public void loadInfo() {
		Map<String, String> info = VideoUtils.readFileToMap(getInfoFile());
		this.localName = info.get(LOCALNAME);
		this.birth     = info.get(BIRTH);
		this.height    = info.get(HEIGHT);
		this.bodySize  = info.get(BODYSIZE);
		this.debut     = info.get(DEBUT);
	}

	private void saveInfo() {
		Map<String, String> info = new HashMap<String, String>();
		info.put(NAME, name);
		info.put(BODYSIZE, bodySize);
		info.put(DEBUT, debut);
		info.put(HEIGHT, height);
		info.put(LOCALNAME, localName);
		info.put(BIRTH, birth);
		VideoUtils.saveFileFromMap(getInfoFile(), info);
	}

	private File getInfoFile() {
		return new File(videoStoragePaths[0], name + FileUtils.EXTENSION_SEPARATOR + CRAZY.EXT_ACTRESS);
	}
	
	@Override
	public int compareTo(Actress comp) {
		switch (sort) {
		case NAME:
			return StringUtils.compareToIgnoreCase(this.getName(), comp.getName());
		case BIRTH:
			return StringUtils.compareToIgnoreCase(this.getBirth(), comp.getBirth());
		case BODY:
			return StringUtils.compareToIgnoreCase(this.getBodySize(), comp.getBodySize());
		case HEIGHT:
			return StringUtils.compareToIgnoreCase(this.getHeight(), comp.getHeight());
		case DEBUT:
			return StringUtils.compareToIgnoreCase(this.getDebut(), comp.getDebut());
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
		return String.format("%s Score %s %s %s %s %s %s [%s]",
						name, getScore(), StringUtils.trimToEmpty(birth), StringUtils.trimToEmpty(bodySize), StringUtils.trimToEmpty(debut), StringUtils.trimToEmpty(height), StringUtils.trimToEmpty(localName), videoList.size());
	}

}
