package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance
@DiscriminatorColumn(length = 64)
public class ContentOfNews implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long				id;
	@Column(name = "Title", length = 128)
	protected String			title				= null;
	@Column(name = "Content", length = 131073)
	protected String			content				= null;
	@Column(name = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				date				= new Date();
	@Column(name = "Type")
	protected String			type				= null;
	@Transient
	protected boolean			haveSelected		= false;

	public ContentOfNews(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public ContentOfNews() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isHaveSelected() {
		return haveSelected;
	}

	public void setHaveSelected(boolean haveSelected) {
		this.haveSelected = haveSelected;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
