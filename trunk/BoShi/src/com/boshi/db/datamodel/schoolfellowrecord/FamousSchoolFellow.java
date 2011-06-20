package com.boshi.db.datamodel.schoolfellowrecord;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( { @NamedQuery(name = "FamousSchoolFellow.ListAll", query = "select DISTINCT t from FamousSchoolFellow t order by t.id DESC"),
	@NamedQuery(name = "FamousSchoolFellow.Amount", query = "select COUNT(DISTINCT t) from FamousSchoolFellow t") })
@Entity
@Table(name = "FamousSchoolFellow")
public class FamousSchoolFellow implements Serializable {

	//从新闻内容模仿
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long				id;
	@Column(name = "Title", length = 128)
	protected String			title				= null;
	@Column(name = "Content", length = 131073)
	protected String			content				= null;
	@Transient
	protected boolean			haveSelected		= false;
	
//按照网上资料模仿	
	private byte[] portrait;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    public byte[] getPortrait() {
    		return portrait;
         }
    public void setPortrait(byte[] portrait) {
        	this.portrait = portrait;
        }

    
//构造函数
    
    public FamousSchoolFellow(String title, String content,byte[] portrait) {
		this.title = title;
		this.content = content;
		this.portrait=portrait;
	}
    
    public FamousSchoolFellow(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public FamousSchoolFellow() {
	}
    
//从新闻内容模仿过来的    
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
	
	public boolean isHaveSelected() {
		return haveSelected;
	}

	public void setHaveSelected(boolean haveSelected) {
		this.haveSelected = haveSelected;
	}
}
