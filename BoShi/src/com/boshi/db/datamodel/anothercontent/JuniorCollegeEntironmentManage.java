package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeEntironmentManage")
public class JuniorCollegeEntironmentManage extends ContentOfAnother {
	// 大专环境管理
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeEntironmentManage(String content) {
		super("JuniorCollegeEntironmentManage", "环境管理", content);
	}

	public JuniorCollegeEntironmentManage() {
		super.id = "JuniorCollegeEntironmentManage";
		super.title = "环境管理";
	}
}
