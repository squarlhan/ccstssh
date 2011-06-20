package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorNotices.ListAll", query = "select DISTINCT t from JuniorNotices t order by t.date DESC"),
	@NamedQuery(name = "JuniorNotices.Amount", query = "select COUNT(DISTINCT t) from JuniorNotices t") })
@Entity
@Table(name = "JuniorNotices")

public class JuniorNotices extends ContentOfNews implements Serializable {
	// 师芳通知
	private static final long	serialVersionUID	= 1L;

	public JuniorNotices(String title, String content) {
		super(title, content);
	}

	public JuniorNotices() {
	}
}
