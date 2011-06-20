package com.boshi.db.datamodel.user;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(length = 512)
public class User implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@Column(name = "name", length = 16)
	protected String			name				= null;
	@Column(name = "password", length = 16)
	protected String			password			= null;
	@Column(name = "power")
	protected String			power				= null;
	@Transient
	protected String			repassword			= null;
	@Transient
	protected boolean			haveSelected		= false;

	public User(String name, String password, String power) {
		this.name = name;
		this.password = password;
		this.power = power;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHaveSelected() {
		return haveSelected;
	}

	public void setHaveSelected(boolean haveSelected) {
		this.haveSelected = haveSelected;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

}
