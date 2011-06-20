package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "EnvirManagement.ListAll", query = "select DISTINCT t from EnvirManagement t order by t.date DESC"),
		@NamedQuery(name = "EnvirManagement.Amount", query = "select COUNT(DISTINCT t) from EnvirManagement t") })
@Entity
@Table(name = "EnvirManagement")
public class EnvirManagement extends ContentOfNews implements Serializable {

	private static final long	serialVersionUID	= 1L;

	public EnvirManagement(String title, String content) {
		super(title, content);
	}

	public EnvirManagement() {
	}
}
