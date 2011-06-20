package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegePersonAbilityTrain")
public class JuniorCollegePersonAbilityTrain extends ContentOfAnother {
	// 大专人才培养
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegePersonAbilityTrain(String content) {
		super("JuniorCollegePersonAbilityTrain", "人才培养", content);
	}

	public JuniorCollegePersonAbilityTrain() {
		super.id = "JuniorCollegePersonAbilityTrain";
		super.title = "人才培养";
	}
}
