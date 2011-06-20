package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeCulture")
public class JuniorCollegeCulture extends ContentOfAnother {
	// 大专校园文化
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeCulture(String content) {
		super("JuniorCollegeCulture", "校园文化", content);
	}

	public JuniorCollegeCulture() {
		super.id = "JuniorCollegeCulture";
		super.title = "校园文化";
	}
}
