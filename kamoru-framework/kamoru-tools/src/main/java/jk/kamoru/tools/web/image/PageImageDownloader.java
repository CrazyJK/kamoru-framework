package jk.kamoru.tools.web.image;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jk.kamoru.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 입력된 웹페이지 URL에 있는 이미지를 모두 다운 받는다.
 * @author kamoru
 */
public class PageImageDownloader {

	private static final Logger logger = LoggerFactory.getLogger(PageImageDownloader.class);

	/**
	 * 이미지가 있는 페이지 URL 
	 */
	private String pageUrl;
	/**
	 * 이미지 저장할 폴더
	 */
	private String downloadDir;
	/**
	 * 이미지 제목 prefix
	 */
	private String titlePrefix;
	/**
	 * 이미지 제목으로 사용할 태그의 CSS Query
	 */
	private String titleCssQuery;
	/**
	 * URL의 페이지 번호. 게시판 같은 경우에 해당
	 */
	private int pageNo;

	/**
	 * 다운 받을 최소 이미지 사이즈
	 */
	private long minimumSize;
	
	private boolean proxy;
	private String proxyHostName;
	private String proxyHostValue;
	private String proxyPortName;
	private int proxyPortValue;
	
	/**
	 * 웹페이지 이미지 다운로더<br>
	 * proxy 설정이 필요하면, {@link #setProxyInfo(boolean, String, String, String, int)} 함수를 추가하라.
	 * @param pageUrl 이미지가 있는 페이지 URL
	 * @param downloadDir 이미지 저장할 폴더
	 */
	public PageImageDownloader(String pageUrl, String downloadDir) {
		this(pageUrl, downloadDir, 0, null, null);
	}
	
	/**
	 * 웹페이지 이미지 다운로더<br>
	 * proxy 설정이 필요하면, {@link #setProxyInfo(boolean, String, String, String, int)} 함수를 추가하라.
	 * @param pageUrl 이미지가 있는 페이지 URL
	 * @param downloadDir 이미지 저장할 폴더
	 * @param pageNo URL의 페이지 번호. 게시판 같은 경우에 해당
	 */
	public PageImageDownloader(String pageUrl, String downloadDir, int pageNo) {
		this(pageUrl, downloadDir, pageNo, null, null);
	}
	
	/**
	 * 웹페이지 이미지 다운로더<br>
	 * proxy 설정이 필요하면, {@link #setProxyInfo(boolean, String, String, String, int)} 함수를 추가하라.
	 * @param pageUrl 이미지가 있는 페이지 URL
	 * @param downloadDir 이미지 저장할 폴더
	 * @param pageNo URL의 페이지 번호. 게시판 같은 경우에 해당
	 * @param titlePrefix 이미지 제목 prefix
	 */
	public PageImageDownloader(String pageUrl, String downloadDir, int pageNo, String titlePrefix) {
		this(pageUrl, downloadDir, pageNo, titlePrefix, null);
	}
	
	/**
	 * 웹페이지 이미지 다운로더<br>
	 * proxy 설정이 필요하면, {@link #setProxyInfo(boolean, String, String, String, int)} 함수를 추가하라.
	 * @param pageUrl 이미지가 있는 페이지 URL
	 * @param downloadDir 이미지 저장할 폴더
	 * @param pageNo URL의 페이지 번호. 게시판 같은 경우에 해당
	 * @param titlePrefix 이미지 제목 prefix
	 * @param titleCssQuery 이미지 제목으로 사용할 태그의 CSS Query 
	 */
	public PageImageDownloader(String pageUrl, String downloadDir, int pageNo, String titlePrefix, String titleCssQuery) {
		super();
		this.pageUrl = pageUrl;
		this.downloadDir = downloadDir;
		this.pageNo = pageNo;
		this.titlePrefix = titlePrefix;
		this.titleCssQuery = titleCssQuery;
	}
	
	/**
	 * 다운로드 받을 이미지의 최소 사이즈. 단위 byte
	 * @param minimumSize
	 */
	public void setMinimumDownloladSize(long minimumSize) {
		this.minimumSize = minimumSize;
	}
	
	/**
	 * 다운로드 수행. {@link java.util.concurrent.ExecutorService}를 이용한 비동기 방식
	 * @param executorService
	 * @return 다운로드 결과
	 */
	public Future<DownloadResult> download(final ExecutorService executorService) {
		return executorService.submit(new Callable<DownloadResult>() {

			@Override
			public DownloadResult call() throws Exception {
				logger.debug("task start - {}", pageUrl);
				return download();
			}
		});
	}
	
	/**
	 *  다운로드를 수행해 이미지를 저장한다.
	 * @return 다운로드 결과
	 */
	public DownloadResult download() {
		
		try {
			// set proxy
			if (proxy) {
				System.setProperty(proxyHostName, proxyHostValue);
				System.setProperty(proxyPortName, String.valueOf(proxyPortValue));
			}
			
			// jsoup HTML parser를 이용해 접속해서 이미지 찾기
			Document document;
			try {
				// connect url
				document = Jsoup.connect(pageUrl).get();
				if (document == null) {
//					logger.error("문서가 비었음");
					return new DownloadResult(false, pageNo, "문서가 비었음 " + pageUrl);
				}
			} catch (IOException e) {
//				logger.error("접속이 안됨 : {} [{}]", e.getMessage(), pageUrl);
				return new DownloadResult(false, pageNo, "접속이 안됨 " + e.getMessage() + " " + pageUrl);
			}
			
			// 페이지 타이틀 구하기
			String documentTitle = document.title();
			String titleByCSS = titleCssQuery != null ? document.select(titleCssQuery).first().text() : null;
			String title = (StringUtils.isEmpty(titlePrefix) ? "" : titlePrefix + "-") 
					+ (pageNo == 0 ? "" : pageNo + "-")
					+ (StringUtils.isEmpty(titleByCSS) ? documentTitle : titleByCSS);
			
			if (StringUtils.isEmpty(title)) {
//				logger.error("제목을 결정할 수 없음 {}", document);
				return new DownloadResult(false, pageNo, "제목을 결정할 수 없음 " + pageUrl);
			}
			
			// img 태그 찾기
			Elements imgTags = document.getElementsByTag("img");
			int foundImageCount = imgTags.size();	
			if (foundImageCount == 0) {
//				logger.error("이미지가 없음 {}", pageUrl);
				return new DownloadResult(false, pageNo, "이미지가 없음 " + pageUrl);
			}
		
			// 페이지 안에 이미지들 다운로드
			int count = 0;
			for (Element imgTag : imgTags) {
				String imgSrc = imgTag.attr("src");
	
				if (!StringUtils.isEmpty(imgSrc)) {
					// download thread start
					ImageDownloader imageDownload = new ImageDownloader(imgSrc, String.format("%s-%s", title, ++count), downloadDir, minimumSize); 
					imageDownload.start();
				}
			}
			return new DownloadResult(true, pageNo, "예상 다운로드 이미지 개수 " + count);
		
		}
		catch (Exception e) {
			return new DownloadResult(false, pageNo, " 예기치 않은 에러 " + e.getMessage() + " " + pageUrl);
		}
		finally {
			// release proxy
			if (proxy) {
				System.clearProperty(proxyHostName);
				System.clearProperty(proxyPortName);
			}
		}
	}

	/**
	 * proxy 설정<br>
	 * ref. http://docs.xrath.com/java/se/6/docs/ko/technotes/guides/net/proxies.html
	 * @param proxy 프록시 사용여부
	 * @param proxyHostName 
	 * @param proxyHostValue
	 * @param proxyPortName
	 * @param proxyPortValue
	 */
	public void setProxyInfo(boolean proxy, String proxyHostName, String proxyHostValue, String proxyPortName, int proxyPortValue) {
		this.proxy = proxy;
		this.proxyHostName = proxyHostName;
		this.proxyHostValue = proxyHostValue;
		this.proxyPortName = proxyPortName;
		this.proxyPortValue = proxyPortValue;
	}
	
	public static class DownloadResult {
		public Boolean result;
		public Integer no;
		public String message = "";
		
		public DownloadResult(Boolean result, Integer no) {
			super();
			this.result = result;
			this.no = no;
		}
		
		public DownloadResult(Boolean result, Integer no, String message) {
			super();
			this.result = result;
			this.no = no;
			this.message = message;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("DownloadResult [result=%s, no=%s, message=%s]", result, no, message);
		}
	}

}
