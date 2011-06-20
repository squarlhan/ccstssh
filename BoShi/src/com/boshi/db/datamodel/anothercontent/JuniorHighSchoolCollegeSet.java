package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorHighSchoolCollegeSet")
public class JuniorHighSchoolCollegeSet extends ContentOfAnother {
	// 初中学科设置
	private static final long	serialVersionUID	= 1L;

	public JuniorHighSchoolCollegeSet(String content) {
		super("JuniorHighSchoolCollegeSet", "初中学科设置", content);
	}

	public JuniorHighSchoolCollegeSet() {
		super.id = "JuniorHighSchoolCollegeSet";
		super.title = "初中学科设置";
	}
}
