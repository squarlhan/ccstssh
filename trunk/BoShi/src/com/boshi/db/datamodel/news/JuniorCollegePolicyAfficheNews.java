package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegePolicyAfficheNews.ListAll", query = "select DISTINCT t from JuniorCollegePolicyAfficheNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegePolicyAfficheNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegePolicyAfficheNews t") })
@Entity
@Table(name = "JuniorCollegePolicyAfficheNews")
public class JuniorCollegePolicyAfficheNews extends ContentOfNews implements Serializable {
	// 大专政策公布
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegePolicyAfficheNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegePolicyAfficheNews() {
	}
}
