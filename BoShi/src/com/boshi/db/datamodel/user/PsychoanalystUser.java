package com.boshi.db.datamodel.user;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "PsychoanalystUser.ListAll", query = "select DISTINCT t from PsychoanalystUser t order by t.name"),
		@NamedQuery(name = "PsychoanalystUser.Amount", query = "select COUNT(DISTINCT t) from PsychoanalystUser t") })
@Entity
@Table(name = "PsychoanalystUser")
public class PsychoanalystUser extends User implements Serializable {
	private static final long	serialVersionUID	= 1L;

	public PsychoanalystUser(String name, String password) {
		super(name, password, "psychoanaly");
	}

	public PsychoanalystUser() {
		super.setPower("psychoanaly");
	}
}
