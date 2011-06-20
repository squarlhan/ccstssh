package com.boshi.db.datamodel.news;

import javax.persistence.*;

import java.io.Serializable;

@NamedQueries( {
		@NamedQuery(name = "JuniorCollegeOnlineSignUpNews.ListAll", query = "select DISTINCT t from JuniorCollegeOnlineSignUpNews t order by t.date DESC"),
		@NamedQuery(name = "JuniorCollegeOnlineSignUpNews.CheckDump", query = "select t from JuniorCollegeOnlineSignUpNews t where t.examid = :examid"),
		@NamedQuery(name = "JuniorCollegeOnlineSignUpNews.find", query = "select DISTINCT t from JuniorCollegeOnlineSignUpNews t where t.examid = :examid and t.pid = :pid"),
		@NamedQuery(name = "JuniorCollegeOnlineSignUpNews.Amount", query = "select COUNT(DISTINCT t) from JuniorCollegeOnlineSignUpNews t") })
@Entity
@Table(name = "JuniorCollegeOnlineSignUpNews")
public class JuniorCollegeOnlineSignUpNews extends ContentOfNews implements Serializable {
	// 在线报名
	private static final long	serialVersionUID	= 1L;
	@Column(name = "Name", length = 16)
	private String				name				= null;
	@Column(name = "Sex", length = 8)
	private String				sex					= null;
	@Column(name = "isAccept", length = 8)
	private String					isaccept;
	@Column(name = "Examid", length = 20)
	private String				examid				= null;
	@Column(name = "Pid", length = 64)
	private String				pid	= null;
	@Column(name = "Photo", length = 255)
	private String				photo				= null;
	@Column(name = "Profession", length = 64)
	private String				profession				= null;
	@Column(name = "Mobile", length = 20)
	private String				mobile				= null;
	@Column(name = "Phone", length = 20)
	private String				phone				= null;

	public JuniorCollegeOnlineSignUpNews(String title, String content) {
		super(title, content);
	}

	public JuniorCollegeOnlineSignUpNews() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsaccept() {
		return isaccept;
	}

	public void setIsaccept(String isaccept) {
		this.isaccept = isaccept;
	}

	public String getExamid() {
		return examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
