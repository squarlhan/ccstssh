package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorHighSchoolExcellenceTeacher")
public class JuniorHighSchoolExcellenceTeacher extends ContentOfAnother {
	// 初中优秀教师
	private static final long	serialVersionUID	= 1L;

	public JuniorHighSchoolExcellenceTeacher(String content) {
		super("JuniorHighSchoolExcellenceTeacher", "初中优秀教师", content);
	}

	public JuniorHighSchoolExcellenceTeacher() {
		super.id = "JuniorHighSchoolExcellenceTeacher";
		super.title = "初中优秀教师";
	}
}
