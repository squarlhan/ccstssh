package com.boshi.db.datamodel.news;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeInviteNews.ListAll", query = "select DISTINCT t from JuniorCollegeInviteNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeInviteNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeInviteNews t") })
@Entity
@Table(name = "JuniorCollegeInviteNews")
public class JuniorCollegeInviteNews extends ContentOfNews implements Serializable {
	// 大专招聘新闻
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeInviteNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeInviteNews() {
	}
}
