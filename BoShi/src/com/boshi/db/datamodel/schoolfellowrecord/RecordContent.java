package com.boshi.db.datamodel.schoolfellowrecord;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

import com.boshi.db.datamodel.user.SchoolfellowUser;

@NamedQueries( { @NamedQuery(name = "RecordContent.ListAll", query = "select DISTINCT t from RecordContent t order by t.date DESC"),
		@NamedQuery(name = "RecordContent.Amount", query = "select COUNT(DISTINCT t) from RecordContent t") })
@Entity
@Table(name = "RecordContent")
public class RecordContent implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long				id;
	@Column(name = "Content", length = 1000)
	private String				content				= null;
	@Column(name = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date				date				= new Date();
	@ManyToOne(fetch = FetchType.EAGER)
	private SchoolfellowUser	schoolfellowUser	= new SchoolfellowUser();
	@Transient
	protected boolean			haveSelected		= false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SchoolfellowUser getSchoolfellowUser() {
		return schoolfellowUser;
	}

	public void setSchoolfellowUser(SchoolfellowUser schoolfellowUser) {
		this.schoolfellowUser = schoolfellowUser;
	}

}
