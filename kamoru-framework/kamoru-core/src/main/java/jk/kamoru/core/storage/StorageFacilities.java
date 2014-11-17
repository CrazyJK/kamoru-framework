package jk.kamoru.core.storage;

import java.util.Set;

/**<pre>
 * 스토리지 저장소
 * {@link Storage} 모음을 관리. 스토리지 생성, 삭제, 스토리지간의 엘리먼트 이동
 * </pre>
 * @author kamoru
 *
 * @param <E> {@link StorageElement} 구현체
 * @param <V>
 * @param <N>
 */
public interface StorageFacilities<E extends Storage.Element> {

	/**
	 * 스토리지 반환
	 * @param index 찾을 인덱스
	 * @return 스토리지
	 */
	Storage<E> get(int index);

	/**
	 * 스토리지 반환. {@link Storage#getName()}으로 찾는다
	 * @param name
	 * @return 스토리지
	 */
	Storage<E> get(String name);
	
	/**
	 * 스토리지간의 엘리먼트 이동
	 * @param element 이동할 엘리먼트
	 * @param fromStorage 엘리먼트가 있는 스토리지
	 * @param toStorage 이동할 스토리지
	 * @throws StorageException fromStorage에 엘리먼트가 없거나, toStorage에 넣지 못할 경우
	 */
	void moveTo(E element, Storage<E> fromStorage, Storage<E> toStorage) throws StorageException;
	
	/**
	 * 스토리지간의 엘리먼트 이동
	 * @param element 이동할 엘리먼트
	 * @param fromName 엘리먼트가 있는 스토리지 이름
	 * @param toName 이동할 스토리지 이름
	 * @throws StorageException fromStorage에 엘리먼트가 없거나, toStorage에 넣지 못할 경우
	 */
	void moveTo(E element, String fromName, String toName) throws StorageException;
	
	/**
	 * 스토리지간의 엘리먼트 이동
	 * @param element 이동할 엘리먼트
	 * @param fromIndex 엘리먼트가 있는 스토리지 인덱스
	 * @param toIndex 이동할 스토리지 인덱스
	 * @throws StorageException fromStorage에 엘리먼트가 없거나, toStorage에 넣지 못할 경우
	 */
	void moveTo(E element, int fromIndex, int toIndex) throws StorageException;
	
	/**
	 * 스토리지 삭제. {@link Storage#deleteAll()} 호출
	 * @param index 삭제할 스토리지 인덱스
	 * @throws StorageException 스토리지를 찾지 못할 경우
	 */
	void delete(int index) throws StorageException;
	
	/**
	 * 스토리지 삭제. {@link Storage#deleteAll()} 호출
	 * @param name 삭제할 스토리지 이름
	 * @throws StorageException 스토리지를 찾지 못할 경우
	 */
	void delete(String name) throws StorageException;
	
	/**
	 * 스토리지 삭제. {@link Storage#deleteAll()} 호출
	 * @param storage 삭제할 스토리지
	 */
	void delete(Storage<E> storage);
	
	/**
	 * 모든 스토리지 삭제. 각 스토리지의 {@link Storage#deleteAll()} 호출
	 */
	void deleteAll();
	
	/**
	 * 스토리지 제거
	 * @param index 제거할 스토리지 인덱스
	 * @throws StorageException 스토리지를 찾지 못할 경우
	 */
	void remove(int index) throws StorageException;
	
	/**
	 * 스토리지 제거
	 * @param name 제거할 스토리지 이름
	 * @throws StorageException 스토리지를 찾지 못할 경우
	 */
	void remove(String name) throws StorageException;
	
	/**
	 * 스토리지 제거
	 * @param storage 제거할 스토리지
	 */
	void remove(Storage<E> storage);
	
	/**
	 * 스토리지 제거
	 */
	void removeAll();
	
	/**
	 * 스토리지 이름 셋트
	 * @return
	 */
	Set<String> nameSet();
	
	/**
	 * 스토리지가 있는지
	 * @param name 확인할 스토리지 이름
	 * @return 있으면 true
	 */
	boolean contains(String name);
	
	/**
	 * 스토리지가 있는지
	 * @param storage 확인할 스토리지
	 * @return 있으면 true
	 */
	boolean contains(Storage<E> storage);
	
	/**
	 * {@link Storage#initialize()}로 초기화된 스토리지 생성 
	 * @param name 스토리지 이름
	 * @return 생성된 스토리지
	 * @throws StorageException 스토리지 이름이 이미 있을 경우
	 */
	Storage<E> create(String name) throws StorageException;

	/**
	 * {@link Storage#initialize()}로 초기화된 스토리지 생성 
	 * @param name 스토리지 이름
	 * @param capacity 스토리지 용량
	 * @return 생성된 스토리지
	 * @throws StorageException 스토리지 이름이 이미 있을 경우
	 */
	Storage<E> create(String name, long capacity) throws StorageException;
}
