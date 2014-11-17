package jk.kamoru.tools.load.task;

import java.net.URL;
import java.util.List;

import lombok.Data;

@Data
public class LoadData {

	URL loginURL;
	String loginParam;
	List<User> users;
	List<URL> loadURLs;

	URL getLoadURL() {
		return loadURLs.get((int) (Math.random() * loadURLs.size()));
	}

	String getLoginParam() {
		User user = getUser();
		return String.format(loginParam, user.getId(), user.getPassword());
	}
	
	private User getUser() {
		return users.get((int) (Math.random() * users.size()));
	}
	
	@Data
	public static class User {
		String id;
		String password;
		public User() {}
		public User(String id, String password) {
			super();
			this.id = id;
			this.password = password;
		}
	}
	
}
