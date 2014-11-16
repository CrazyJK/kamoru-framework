package jk.kamoru.crazy.domain;

import java.io.Serializable;
import java.util.List;

import jk.kamoru.crazy.CRAZY;
import lombok.Data;

/**
 * 비디오 검색 form bean
 * @author kamoru
 */
@Data
public class Search implements Serializable {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	/**
	 * 검색조건 : 검색어
	 */
	String searchText;
	/**
	 * 검색조건 : 추가 여부. 조건 {@link #existVideo}, {@link #existSubtitles}
	 */
	boolean addCond = false;
	/**
	 * 검색조건 : 비디오 존재
	 */
	boolean existVideo = false;
	/**
	 * 검색조건 : 자막 존재
	 */
	boolean existSubtitles;
	/**
	 * 검색조건 : 랭킹 범위
	 */
	List<Integer> rankRange;
	/**
	 * 검색조건 : 플레이 횟수 
	 */
	Integer playCount = 0;

	/**
	 * View type
	 */
	View listViewType = View.S;
	/**
	 * 정렬 방법 
	 */
	SortVideo sortMethod = SortVideo.M;
	/**
	 * 역정렬 여부
	 */
	boolean sortReverse = true;

	/**
	 * 검색조건 : 선택된 스튜디오
	 */
	List<String> selectedStudio;
	/**
	 * 검색조건 : 선택된 배우
	 */
	List<String> selectedActress;

	/**
	 * 스튜디오 화면 볼지 여부
	 */
	boolean viewStudioDiv = false;
	/**
	 * 여배우 화면 볼지 여부
	 */
	boolean viewActressDiv = false;

	/* Not use
	boolean neverPlay = false;
	boolean oldVideo = false;
	String studio;
	String title;
	String opus;
	String actress;
	InequalitySign rankSign = InequalitySign.gt;
	boolean zeroRank = false;
	Integer rank = -2;
	*/

}
