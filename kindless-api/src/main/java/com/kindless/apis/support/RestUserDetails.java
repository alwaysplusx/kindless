package com.kindless.apis.support;

import com.harmony.umbrella.security.userdetails.IdentityUserDetails;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestUserDetails implements IdentityUserDetails, CredentialsContainer {

    private static final long serialVersionUID = -8613756955182655976L;
    private Long userId;
	private String username;
	private String password;
	private boolean accountNonLocked;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	private boolean enabled;
	@Builder.Default
	private Set<String> plainTextAuthorities = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return plainTextAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public void eraseCredentials() {
		password = null;
	}

	@Override
	public String toString() {
		return "RestUserDetails{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password=[PROTECTED]" +
				", accountNonLocked=" + accountNonLocked +
				", accountNonExpired=" + accountNonExpired +
				", credentialsNonExpired=" + credentialsNonExpired +
				", enabled=" + enabled +
				", plainTextAuthorities=" + plainTextAuthorities +
				'}';
	}

}
