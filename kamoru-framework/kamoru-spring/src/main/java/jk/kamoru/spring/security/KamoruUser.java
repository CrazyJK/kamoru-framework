package jk.kamoru.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**로그인 유저 Bean
 * <p>하드코딩된 패스워드와 모든 권한이 있는 사용자
 * @author kamoru
 *
 */
@Data
public class KamoruUser implements UserDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	private String username;
	private List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
	private String remoteAddr;
	
	public KamoruUser(String username) {
		this.username = username;
	}

	public void addRole(String role) {
		this.authorites.add(new SimpleGrantedAuthority(role));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}

	@Override
	public String getPassword() {
		return "crazyjk";
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isAccountNonExpired() && isAccountNonLocked();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authorites == null) ? 0 : authorites.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof KamoruUser)) {
			return false;
		}
		KamoruUser other = (KamoruUser) obj;
		if (authorites == null) {
			if (other.authorites != null) {
				return false;
			}
		} else if (!authorites.equals(other.authorites)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	
}
