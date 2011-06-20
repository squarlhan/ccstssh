package com.boshi.db.datamodel.user;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "SeniorHighSchoolAdminUser.ListAll", query = "select DISTINCT t from SeniorHighSchoolAdminUser t order by t.name"),
		@NamedQuery(name = "SeniorHighSchoolAdminUser.Amount", query = "select COUNT(DISTINCT t) from SeniorHighSchoolAdminUser t") })
@Entity
@Table(name = "SeniorHighSchoolAdminUser")
public class SeniorHighSchoolAdminUser extends User implements Serializable {
	private static final long	serialVersionUID	= 1L;

	public SeniorHighSchoolAdminUser(String name, String password) {
		super(name, password, "seniorHighSchoolAdminUser");
	}

	public SeniorHighSchoolAdminUser() {
		super.setPower("seniorHighSchoolAdminUser");
	}
}
