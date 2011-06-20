package com.boshi.db.datamodel.user;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorHighSchoolAdminUser.ListAll", query = "select DISTINCT t from JuniorHighSchoolAdminUser t order by t.name"),
		@NamedQuery(name = "JuniorHighSchoolAdminUser.Amount", query = "select COUNT(DISTINCT t) from JuniorHighSchoolAdminUser t") })
@Entity
@Table(name = "JuniorHighSchoolAdminUser")
public class JuniorHighSchoolAdminUser extends User implements Serializable {
	private static final long	serialVersionUID	= 1L;

	public JuniorHighSchoolAdminUser(String name, String password) {
		super(name, password, "highSchoolAdmin");
	}

	public JuniorHighSchoolAdminUser() {
		super.setPower("highSchoolAdmin");
	}
}
