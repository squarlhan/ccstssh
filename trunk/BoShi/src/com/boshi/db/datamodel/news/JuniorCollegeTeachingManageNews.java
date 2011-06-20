package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeTeachingManageNews.ListAll", query = "select DISTINCT t from JuniorCollegeTeachingManageNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeTeachingManageNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeTeachingManageNews t") })
@Entity
@Table(name = "JuniorCollegeTeachingManageNews")
public class JuniorCollegeTeachingManageNews extends ContentOfNews implements Serializable {
	// 教学管理
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeTeachingManageNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeTeachingManageNews() {
	}
}
