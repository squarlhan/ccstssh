package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeCollegeSet")
public class JuniorCollegeCollegeSet extends ContentOfAnother {
	// 大专院系设置
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeCollegeSet(String content) {
		super("JuniorCollegeCollegeSet", "院系设置", content);
	}

	public JuniorCollegeCollegeSet() {
		super.id = "JuniorCollegeCollegeSet";
		super.title = "院系设置";
	}
}
