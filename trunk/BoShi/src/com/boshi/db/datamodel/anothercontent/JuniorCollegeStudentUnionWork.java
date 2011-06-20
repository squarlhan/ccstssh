package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeStudentUnionWork")
public class JuniorCollegeStudentUnionWork extends ContentOfAnother {
	// 大专学生会工作
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeStudentUnionWork(String content) {
		super("JuniorCollegeStudentUnionWork", "学生会工作", content);
	}

	public JuniorCollegeStudentUnionWork() {
		super.id = "JuniorCollegeStudentUnionWork";
		super.title = "学生会工作";
	}
}
