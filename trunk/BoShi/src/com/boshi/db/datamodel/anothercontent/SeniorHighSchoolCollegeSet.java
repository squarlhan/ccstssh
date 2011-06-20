package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "SeniorHighSchoolCollegeSet")
public class SeniorHighSchoolCollegeSet extends ContentOfAnother {
	// 高中学科设置
	private static final long	serialVersionUID	= 1L;

	public SeniorHighSchoolCollegeSet(String content) {
		super("SeniorHighSchoolCollegeSet", "高中学科设置", content);
	}

	public SeniorHighSchoolCollegeSet() {
		super.id = "SeniorHighSchoolCollegeSet";
		super.title = "高中学科设置";
	}
}
