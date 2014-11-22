package jk.kamoru.tools.load;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jk.kamoru.core.task.TaskExecutor;
import jk.kamoru.tools.load.task.LoadData;

public class CrazyImageLoadTester {

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {

		LoadData loadData = new LoadData();
		loadData.setLoginURL(new URL("http://jk.kamoru:8080/crazy/j_spring_security_check"));
		loadData.setLoginParam("j_username=%s&j_password=%s");
		loadData.setUsers(Arrays.asList(new LoadData.User("user1", "crazyjk"), new LoadData.User("user2", "crazyjk"), new LoadData.User("user3", "crazyjk")));
		List<URL> loadURLs = new ArrayList<URL>();
		for (int i = 0; i < 21; i++)
			loadURLs.add(new URL(String.format("http://jk.kamoru:8080/crazy/image/%s", i)));
		loadData.setLoadURLs(loadURLs);

		TaskExecutor executer = LoadTesters.newRandomDelayLoadTester(10, 3000, loadData);
		executer.execute(10000);
	}

}
