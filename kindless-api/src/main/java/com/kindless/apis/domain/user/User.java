package com.kindless.apis.domain.user;

import com.kindless.apis.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "u_user")
public class User extends BaseEntity {

	public static final int ACCOUNT_LOCKED = 0;
	public static final int ACCOUNT_UNLOCKED = 1;

	private static final long serialVersionUID = 543961445644373533L;

	private String username;
	private String nickname;
	private String password;
	private String avatar;
	private int gender;
	private String remark;
	private Date registerAt;
	private Date passwordExpiredAt;
	private int accountStatus;
	private Date accountExpiredAt;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private UserSettings userSettings;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private UserDetails userDetails;

	public User() {
	}

	public User(Long userId) {
		this.id = userId;
	}

	public boolean isLocked() {
		return accountStatus == ACCOUNT_LOCKED;
	}

	public boolean isEnabled() {
		return !isLocked();
	}

}
