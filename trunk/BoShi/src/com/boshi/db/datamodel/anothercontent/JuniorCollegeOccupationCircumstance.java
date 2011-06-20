package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeOccupationCircumstance")
public class JuniorCollegeOccupationCircumstance extends ContentOfAnother {
	// 大专就业情况
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeOccupationCircumstance(String content) {
		super("JuniorCollegeOccupationCircumstance", "就业情况", content);
	}

	public JuniorCollegeOccupationCircumstance() {
		super.id = "JuniorCollegeOccupationCircumstance";
		super.title = "就业情况";
	}
}
