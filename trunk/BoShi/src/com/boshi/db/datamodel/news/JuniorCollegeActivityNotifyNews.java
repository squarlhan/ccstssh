package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "JuniorCollegeActivityNotifyNews.ListAll", query = "select DISTINCT t from JuniorCollegeActivityNotifyNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeActivityNotifyNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeActivityNotifyNews t") })
@Entity
@Table(name = "JuniorCollegeActivityNotifyNews")
public class JuniorCollegeActivityNotifyNews extends ContentOfNews implements Serializable {
	// 活动通知
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeActivityNotifyNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeActivityNotifyNews() {
	}
}
