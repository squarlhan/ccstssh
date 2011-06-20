package com.boshi.db.datamodel.anothercontent;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Inheritance
@DiscriminatorColumn(length = 64)
public class ContentOfAnother implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	protected String			id;
	@Column(name = "Tile", length = 32)
	protected String			title				= null;
	@Column(name = "Content", length = 2048)
	protected String			content				= null;

	public ContentOfAnother(String id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public ContentOfAnother() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
