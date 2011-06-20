package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeScientificResearchNews.ListAll", query = "select DISTINCT t from JuniorCollegeScientificResearchNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeScientificResearchNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeScientificResearchNews t") })
@Entity
@Table(name = "JuniorCollegeScientificResearchNews")
public class JuniorCollegeScientificResearchNews extends ContentOfNews implements Serializable {
	// 科研成果
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeScientificResearchNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeScientificResearchNews() {
	}
}
