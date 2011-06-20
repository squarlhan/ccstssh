package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeLiveServiceManual")
public class JuniorCollegeLiveServiceManual extends ContentOfAnother {
	// 大专生活服务指南
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeLiveServiceManual(String content) {
		super("JuniorCollegeLiveServiceManual", "生活服务指南", content);
	}

	public JuniorCollegeLiveServiceManual() {
		super.id = "JuniorCollegeLiveServiceManual";
		super.title = "生活服务指南";
	}
}
