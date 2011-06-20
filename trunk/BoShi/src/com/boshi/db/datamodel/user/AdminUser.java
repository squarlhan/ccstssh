package com.boshi.db.datamodel.user;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "AdminUser")
public class AdminUser extends User implements Serializable {
	private static final long	serialVersionUID	= 1L;

	public AdminUser(String name, String password) {
		super(name, password, "admin");
	}

	public AdminUser() {
		super.setPower("admin");
	}
}
