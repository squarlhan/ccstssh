package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "SeniorHighSchoolCircumstance")
public class SeniorHighSchoolCircumstance extends ContentOfAnother {
	// 高中概况
	private static final long	serialVersionUID	= 1L;

	public SeniorHighSchoolCircumstance(String content) {
		super("SeniorHighSchoolCircumstance", "高中概况", content);
	}

	public SeniorHighSchoolCircumstance() {
		super.id = "SeniorHighSchoolCircumstance";
		super.title = "高中概况";
	}
}
