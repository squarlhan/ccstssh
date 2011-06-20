package com.boshi.db.datamodel.news;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "SeniorHighSchoolRecruitStudentsNews.ListAll", query = "select DISTINCT t from SeniorHighSchoolRecruitStudentsNews t order by t.date DESC"),
		@NamedQuery(name = "SeniorHighSchoolRecruitStudentsNews.Amount", query = "select COUNT(DISTINCT t) from SeniorHighSchoolRecruitStudentsNews t") })
@Entity
@Table(name = "SeniorHighSchoolRecruitStudentsNews")
public class SeniorHighSchoolRecruitStudentsNews extends ContentOfNews implements Serializable {
	// 高中招生新闻
	private static final long	serialVersionUID	= 1L;

	public SeniorHighSchoolRecruitStudentsNews(String title, String content) {
		super(title, content);
	}

	public SeniorHighSchoolRecruitStudentsNews() {
	}
}
