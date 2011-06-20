package com.boshi.db.datamodel.news;

import javax.persistence.*;


import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "LearningResourceShare.ListAll", query = "select DISTINCT t from LearningResourceShare t order by t.date DESC"),
		@NamedQuery(name = "LearningResourceShare.Amount", query = "select COUNT(DISTINCT t) from LearningResourceShare t") })
@Entity
@Table(name = "LearningResourceShare")
public class LearningResourceShare extends ContentOfNews implements Serializable {
//	学习资源共享
	private static final long	serialVersionUID	= 1L;

	public LearningResourceShare(String title, String content) {
		super(title, content);
	}

	public LearningResourceShare() {
	}
}
