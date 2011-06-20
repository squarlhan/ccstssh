package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "SeniorNotices.ListAll", query = "select DISTINCT t from SeniorNotices t order by t.date DESC"),
	@NamedQuery(name = "SeniorNotices.Amount", query = "select COUNT(DISTINCT t) from SeniorNotices t") })
@Entity
@Table(name = "SeniorNotices")

public class SeniorNotices extends ContentOfNews implements Serializable {
	// 高中通知
	private static final long	serialVersionUID	= 1L;

	public SeniorNotices(String title, String content) {
		super(title, content);
	}

	public SeniorNotices() {
	}
}
