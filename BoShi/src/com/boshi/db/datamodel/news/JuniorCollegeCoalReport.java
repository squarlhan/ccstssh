package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
	@NamedQuery(name = "JuniorCollegeCoalReport.ListAll", query = "select DISTINCT t from JuniorCollegeCoalReport t order by t.date DESC"),
	@NamedQuery(name = "JuniorCollegeCoalReport.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeCoalReport t") })
@Entity
@Table(name = "JuniorCollegeCoalReport")

public class JuniorCollegeCoalReport extends ContentOfNews implements Serializable{

	// 煤体报道公布
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeCoalReport(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeCoalReport() {
	}
}
