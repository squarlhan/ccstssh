package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegeIntroduceSchoolBadge")
public class JuniorCollegeIntroduceSchoolBadge extends ContentOfAnother {
	// 校徽介绍
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeIntroduceSchoolBadge(String content) {
		super("JuniorCollegeIntroduceSchoolBadge", "校徽介绍", content);
	}

	public JuniorCollegeIntroduceSchoolBadge() {
		super.id = "JuniorCollegeIntroduceSchoolBadge";
		super.title = "校徽介绍";
	}
}
