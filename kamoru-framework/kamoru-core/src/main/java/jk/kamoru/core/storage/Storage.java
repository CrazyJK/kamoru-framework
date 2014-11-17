package jk.kamoru.core.storage;

import java.util.Collection;

/**<pre>
 * 스토리지 
 * 엘리먼트는 {@link StorageElement}를 구현해야 한다
 * 스토리지는 {@link StorageFacilities}에 포함되어 관리 된다
 * </pre>
 * @author kamoru
 *
 * @param <E> {@link Storage.Element} 구현체
 * @param <N> 엘리먼트 이름
 * @param <V> 엘리먼트 밸류
 */
public interface Storage<E extends Storage.Element> {

	/**
	 * 스토리지 초기화. 용량 제한 없음
	 * @param name 스토리지 이름
	 */
	void initialize(String name);
	/**
	 * 스토리지 초기화. 
	 * @param name 스토리지 이름
	 * @param capacity 스토리지 용량
	 */
	void initialize(String name, long capacity);

	/**
	 * 스토리지 이름 반환
	 * @return 이름
	 */
	String getName();
	
	/**
	 * 엘리먼트를 추가.
	 * @param element 추가할 요소
	 * @throws StorageException 용량이 초과될 경우
	 */
	void add(E element) throws StorageException;
	/**
	 * 엘리먼트 목록을 추가
	 * @param cllection 추가할 엘리먼트 목록
	 * @throws StorageException 용량이 초과될 경우
	 */
	void addAll(Collection<E> collection) throws StorageException;

	/**
	 * 엘리먼트를 삭제. 엘리먼트의 {@link StorageElement#delete()} 호출
	 * @param index 찾을 인덱스
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	void delete(int index) throws StorageException;
	/**
	 * 엘리먼트를 삭제. 엘리먼트의 {@link StorageElement#delete()} 호출
	 * @param key 찾을 키
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	void delete(String key) throws StorageException;
	/**
	 * 엘리먼트 삭제. 엘리먼트의 {@link StorageElement#delete()} 호출
	 * @param element 삭제할 엘리먼트
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	void delete(E element) throws StorageException;
	/**
	 * 모든 엘리먼트 삭제.각 엘리먼트의 {@link StorageElement#delete()} 호출
	 */
	void deleteAll();
	
	/**
	 * 엘리먼트 제거
	 * @param index 찾을 인덱스
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	void remove(int index) throws StorageException;
	/**
	 * 엘리먼트 제거. 엘리먼트의 {@link StorageElement#getKey()} 이용
	 * @param key 찾을 키
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	void remove(String key) throws StorageException;
	/**
	 * 엘리먼트 제거
	 * @param element 제거할 엘리먼트
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	void remove(E element) throws StorageException;
	/**
	 * 모든 엘리먼트 제거
	 */
	void removeAll();
	
	/**
	 * 엘리먼트가 있는지
	 * @param element
	 * @return
	 */
	boolean contains(E element);
	
	/**
	 * 엘리먼트 개수
	 * @return
	 */
	int size();
	
	/**
	 * 스토리지 용량 크기
	 * @return 무제한일 경우 -1
	 */
	long getTotalSpace();
	/**
	 * 용량이 정해져 있는 경우 여유 공간
	 * @return 무제한일 경우 -1
	 */
	long getFreeSpace();
	/**
	 * 엘리먼트의 크기 합. 엘리먼트의 {@link StorageElement#getlength()} 이용
	 * @return
	 */
	long getUsableSpace();
	
	/**
	 * 엘리먼트 반환
	 * @param index 찾을 인덱스
	 * @return 엘리먼트
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	E get(int index) throws StorageException;
	/**
	 * 엘리먼트 반환. 엘리먼트의 {@link StorageElement#getKey()} 이용
	 * @param key 찾을 키
	 * @return 엘리먼트
	 * @throws StorageException 엘리먼트를 못찾을 경우
	 */
	E get(String key) throws StorageException;
	
	/**
	 * 찾은 엘리먼트 집합 반환. 엘리먼트의 {@link StorageElement#getQuery()} 이용
	 * @param query 검색어
	 * @return 엘리먼트 집합
	 */
	Collection<E> find(String query);

	/**
	 * 스토리지 파괴
	 */
	void destory();
	
	/**
	 * 스토리지 엘리먼트
	 * @author kamoru
	 */
	interface Element {

		/**
		 * 엘리먼트 이름
		 */
		String getName();
		
		/**
		 * 엘리먼트 크기
		 * @return
		 */
		long getLength();
		
		/**
		 * 검색에 사용될 단어
		 * @return
		 */
		String getQuery();
		
		/**
		 * 엘리먼트 삭제
		 */
		void delete();
		
	}

}
