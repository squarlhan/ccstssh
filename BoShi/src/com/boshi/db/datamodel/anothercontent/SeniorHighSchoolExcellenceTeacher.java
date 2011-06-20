package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "SeniorHighSchoolExcellenceTeacher")
public class SeniorHighSchoolExcellenceTeacher extends ContentOfAnother {
	// 高中优秀教师
	private static final long	serialVersionUID	= 1L;

	public SeniorHighSchoolExcellenceTeacher(String content) {
		super("SeniorHighSchoolExcellenceTeacher", "高中优秀教师", content);
	}

	public SeniorHighSchoolExcellenceTeacher() {
		super.id = "SeniorHighSchoolExcellenceTeacher";
		super.title = "高中优秀教师";
	}
}
