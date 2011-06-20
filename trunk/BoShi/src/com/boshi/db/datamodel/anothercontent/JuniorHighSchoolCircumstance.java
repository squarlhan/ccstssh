package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorHighSchoolCircumstance")
public class JuniorHighSchoolCircumstance extends ContentOfAnother {
	// 初中概况
	private static final long	serialVersionUID	= 1L;

	public JuniorHighSchoolCircumstance(String content) {
		super("JuniorHighSchoolCircumstance", "初中概况", content);
	}

	public JuniorHighSchoolCircumstance() {
		super.id = "JuniorHighSchoolCircumstance";
		super.title = "初中概况";
	}
}
