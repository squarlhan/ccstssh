package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeCircumstance")
public class JuniorCollegeCircumstance extends ContentOfAnother {
	// 大专概况
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeCircumstance(String content) {
		super("JuniorCollegeCircumstance", "学校概况", content);
	}

	public JuniorCollegeCircumstance() {
		super.id = "JuniorCollegeCircumstance";
		super.title = "学校概况";
	}
}
