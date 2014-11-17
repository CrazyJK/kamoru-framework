package jk.kamoru.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 사용자 패스워드 비교<br>
 * 암호화 적용하지 않고, plain text로 비교
 * @author kamoru
 *
 */
public class PlainPasswordEncoder implements PasswordEncoder {

	private static final Logger logger = LoggerFactory.getLogger(PlainPasswordEncoder.class);

	@Override
	public String encode(CharSequence rawPassword) {
		logger.trace("{}", rawPassword);
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		logger.trace("{} - {}", rawPassword, encodedPassword);
		return rawPassword.equals(encodedPassword);
	}

}
