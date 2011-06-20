package com.boshi.action.news.welcomepage;

import java.util.List;

import com.boshi.action.news.SuperListNewsAction;
import com.boshi.action.news.collegenews.ListCollegeNewsAction;
import com.boshi.action.news.juniorcollege.activitynotify.ListJuniorCollegeActivityNotifyAction;

public class ListWelcomePageNewsAction extends SuperListNewsAction {
	private static final long						serialVersionUID			= 1L;
	private ListJuniorCollegeActivityNotifyAction	activityNotify				= null;
	private ListCollegeNewsAction					collegeNews					= null;
	private List<Object>							activityNotifyResultList	= null;
	private List<Object>							collegeNewsResultList		= null;
	private long									lastDoTime					= 0;

	public String execute() {
		if (System.currentTimeMillis() - lastDoTime > 3 * 60 * 1000) {
			activityNotifyResultList = doListActivityNotify();
			collegeNewsResultList = doCollegeNews();
			lastDoTime = System.currentTimeMillis();
		}
		return SUCCESS;
	}

	private List<Object> doListActivityNotify() {
		activityNotify.execute();
		List<Object> list = activityNotify.getResultList();
		int endIndex = 0;
		if (list.size() >= 7)
			endIndex = 7;
		else
			endIndex = list.size();
		return list.subList(0, endIndex);
	}

	private List<Object> doCollegeNews() {
		collegeNews.execute();
		List<Object> list = collegeNews.getResultList();
		int endIndex = 0;
		if (list.size() >= 7)
			endIndex = 7;
		else
			endIndex = list.size();
		return list.subList(0, endIndex);
	}

	public ListJuniorCollegeActivityNotifyAction getActivityNotify() {
		return activityNotify;
	}

	public void setActivityNotify(ListJuniorCollegeActivityNotifyAction activityNotify) {
		this.activityNotify = activityNotify;
	}

	public ListCollegeNewsAction getCollegeNews() {
		return collegeNews;
	}

	public void setCollegeNews(ListCollegeNewsAction collegeNews) {
		this.collegeNews = collegeNews;
	}

	public List<Object> getCollegeNewsResultList() {
		return collegeNewsResultList;
	}

	public void setCollegeNewsResultList(List<Object> collegeNewsResultList) {
		this.collegeNewsResultList = collegeNewsResultList;
	}

	public List<Object> getActivityNotifyResultList() {
		return activityNotifyResultList;
	}

	public void setActivityNotifyResultList(List<Object> activityNotifyResultList) {
		this.activityNotifyResultList = activityNotifyResultList;
	}
}
