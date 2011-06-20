package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
	@NamedQuery(name = "JuniorCollegePolicyRulee.ListAll", query = "select DISTINCT t from JuniorCollegePolicyRulee t order by t.date DESC"),
	@NamedQuery(name = "JuniorCollegePolicyRulee.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegePolicyRulee t") })
@Entity
@Table(name = "JuniorCollegePolicyRulee")

public class JuniorCollegePolicyRulee extends ContentOfNews implements Serializable {

	// 煤体报道公布
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegePolicyRulee(String title, String content) {
		super(title, content);
	}

	public JuniorCollegePolicyRulee() {
	}
}