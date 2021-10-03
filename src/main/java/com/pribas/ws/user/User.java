package com.pribas.ws.user;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.pribas.ws.auth.Token;
import com.pribas.ws.timeline.Timeline;

import lombok.Data;

@Data
@Entity
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;

	@NotNull
	@Size(min = 4, max = 50)
	@UniqueUsername
	private String username;

	@NotNull
	@Size(max = 50)
	@Pattern(regexp = "^(.+)@(.+)$", message = "{pribas.constraint.email.Pattern.message}")
	private String email;

	@NotNull
	@Size(min = 8, max = 50)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{pribas.constraint.password.Pattern.message}")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Timeline> timelines;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Token> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
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
		return true;
	}
}
