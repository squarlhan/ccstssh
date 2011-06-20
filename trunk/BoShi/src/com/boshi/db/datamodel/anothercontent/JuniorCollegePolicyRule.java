package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

@Entity
@Table(name = "JuniorCollegePolicyRule")
public class JuniorCollegePolicyRule extends ContentOfAnother {
	// 大专政策法规
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegePolicyRule(String content) {
		super("JuniorCollegePolicyRule", "政策法规", content);
	}

	public JuniorCollegePolicyRule() {
		super.id = "JuniorCollegePolicyRule";
		super.title = "政策法规";
	}
}
