package jk.kamoru.tools.load.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import jk.kamoru.core.sleep.Sleep;
import jk.kamoru.core.task.Result;
import jk.kamoru.core.task.Task;
import lombok.Cleanup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Load implements Task {

	/** No, Thread, Action, Elapsed time, Content length, Error, Desc */
	private static final String OUTPUT_PATTERN = "%s, %s, %s, %s, %s, %s, %s";

	public static int callCount;
	
	Sleep sleep;
	long delay;
	LoadData loadData;
	
	private String cookies = new String();
	
	@Override
	public Result call() throws Exception {
		LoadResult result = new LoadResult();
		try {
			log.info("call");
			login();
			while (true) {
				result.count();
				load();
				sleep.sleep(delay);
			}
		}
		catch (IOException e) {
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setError(e.getMessage());
		}
		catch (Exception e) {
			throw e;
		}
		return result;
	}

	private void login() throws IOException {
		long startTime = System.currentTimeMillis();
		HttpURLConnection loginConn = (HttpURLConnection)loadData.loginURL.openConnection();
		loginConn.setDoOutput(true);
		loginConn.setInstanceFollowRedirects(false);
		@Cleanup OutputStreamWriter wr = new OutputStreamWriter(loginConn.getOutputStream());
		wr.write(loadData.loginParam);
		wr.flush();

		Map<?, ?> m = loginConn.getHeaderFields();
		if (m.containsKey("Set-Cookie")) {
			Collection<?> c = (Collection<?>)m.get("Set-Cookie");
		    for (Iterator<?> i = c.iterator(); i.hasNext(); ) {
		    	cookies += (String)i.next() + ", ";
	    	}
		}
        long elapsedTime = System.currentTimeMillis() - startTime;
		log.info(String.format(OUTPUT_PATTERN, callCount, "Login", loadData.loginURL, elapsedTime, "", "", cookies));   
	}

	private void load() throws IOException {
		long startTime = System.currentTimeMillis();
		callCount++;
		HttpURLConnection loadConn = (HttpURLConnection)loadData.getLoadURL().openConnection();
		loadConn.setRequestProperty("cookie", cookies);
		@Cleanup BufferedReader br = new BufferedReader(new InputStreamReader(loadConn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String buf = "";
        while ((buf = br.readLine()) != null) {
        	sb.append(buf);
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info(String.format(OUTPUT_PATTERN, callCount, "Load", loadData.getLoadURL(), elapsedTime, sb.length(), "", ""));
	}

}
