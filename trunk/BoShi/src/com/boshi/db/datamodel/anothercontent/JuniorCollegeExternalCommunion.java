package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeExternalCommunion")
public class JuniorCollegeExternalCommunion extends ContentOfAnother {
	// 大专对外交流
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeExternalCommunion(String content) {
		super("JuniorCollegeExternalCommunion", "对外交流", content);
	}

	public JuniorCollegeExternalCommunion() {
		super.id = "JuniorCollegeExternalCommunion";
		super.title = "对外交流";
	}
}
