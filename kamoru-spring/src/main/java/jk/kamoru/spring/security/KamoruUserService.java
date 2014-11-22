package jk.kamoru.spring.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 로그인 요청한 사용자를 찾아주는 service
 * <p>현재는 유일 사용자만 반환. 필요시 DAO와 연결
 * @author kamoru
 *
 */
public class KamoruUserService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		KamoruUser user = new KamoruUser(username);
    	user.addRole("ROLE_USER");
    	user.addRole("ROLE_CRAZY");
		if (user.getUsername().equals("jk.crazy")) {
			user.addRole("ROLE_MANAGER");
		}

		return user;
	}

	
}
