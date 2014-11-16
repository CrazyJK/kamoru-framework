package jk.kamoru.crazy.service;

import java.util.Date;
import java.util.List;

import jk.kamoru.crazy.domain.Action;
import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.History;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.Video;

public interface HistoryService {

	/**<code>History</code> 저장
	 * @param history
	 */
	void persist(History history);
	
	/**opus로 찾기
	 * @param opus
	 * @return list of found history
	 */
	List<History> findByOpus(String opus);

	/**<code>Video</code>로 찾기
	 * @param video
	 * @return list of found history
	 */
	List<History> findByVideo(Video video);

	/**<code>Studio<code>로 찾기
	 * @param studio
	 * @return list of found history
	 */
	List<History> findByStudio(Studio studio);

	/**<code>Actress</code>로 찾기
	 * @param actress
	 * @return list of found history
	 */
	List<History> findByActress(Actress actress);

	/**검색어로 찾기
	 * @param query 검색어
	 * @return list of found history
	 */
	List<History> findByQuery(String query);
	
	/**날자로 찾기
	 * @param date
	 * @return list of found history
	 */
	List<History> findByDate(Date date);

	/**<code>Action</code>로 찾기
	 * @param action
	 * @return
	 */
	List<History> findByAction(Action action);
	
	/**모든 <code>History</code>
	 * @return list of all history
	 */
	List<History> getAll();

	/**opus가 있었는지
	 * @param opus
	 * @return
	 */
	boolean contains(String opus);
	
	/**중복 제거한 비디오 히스토리.  
	 * @return
	 */
	List<History> getDeduplicatedList();
	
}
