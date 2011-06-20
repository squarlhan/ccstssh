package com.boshi.db.datamodel.news;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeSectionOffice.ListAll", query = "select DISTINCT t from JuniorCollegeSectionOffice t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeSectionOffice.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeSectionOffice t") })
@Entity
@Table(name = "JuniorCollegeSectionOffice")
public class JuniorCollegeSectionOffice extends ContentOfNews implements Serializable {
	// 大专科室
	private static final long	serialVersionUID	= 1L;

	public JuniorCollegeSectionOffice(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeSectionOffice() {
	}
}
