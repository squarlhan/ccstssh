package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeSpecializedSet")
public class JuniorCollegeSpecializedSet extends ContentOfAnother {
	// 大专专业设置
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeSpecializedSet(String content) {
		super("JuniorCollegeSpecializedSet", "专业设置", content);
	}

	public JuniorCollegeSpecializedSet() {
		super.id = "JuniorCollegeSpecializedSet";
		super.title = "专业设置";
	}
}
