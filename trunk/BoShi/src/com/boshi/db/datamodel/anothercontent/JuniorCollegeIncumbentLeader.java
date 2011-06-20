package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeIncumbentLeader")
public class JuniorCollegeIncumbentLeader extends ContentOfAnother {
	// 大专现任领导
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeIncumbentLeader(String content) {
		super("JuniorCollegeIncumbentLeader", "现任领导", content);
	}

	public JuniorCollegeIncumbentLeader() {
		super.id = "JuniorCollegeIncumbentLeader";
		super.title = "现任领导";
	}
}
