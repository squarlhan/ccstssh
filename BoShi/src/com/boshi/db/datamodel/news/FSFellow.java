package com.boshi.db.datamodel.news;
import javax.persistence.*;

import java.io.Serializable;
@NamedQueries( { @NamedQuery(name = "FSFellow.ListAll", query = "select DISTINCT t from FSFellow t order by t.date DESC"),
	@NamedQuery(name = "FSFellow.Amount", query = "select COUNT(DISTINCT t) from FSFellow t") })
@Entity
@Table(name = "FSFellow")
public class FSFellow  extends ContentOfNews implements Serializable {

	private static final long	serialVersionUID	= 1L;

	public FSFellow(String title, String content) {
		super(title, content);
	}

	public FSFellow() {
	}
}
