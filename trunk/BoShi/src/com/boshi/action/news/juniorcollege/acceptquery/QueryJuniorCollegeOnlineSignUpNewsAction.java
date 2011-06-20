package com.boshi.action.news.juniorcollege.acceptquery;

import java.util.List;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegeOnlineSignUpNews;


public class QueryJuniorCollegeOnlineSignUpNewsAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeOnlineSignUpNews	content				= new JuniorCollegeOnlineSignUpNews();
	private static final int BUFFER_SIZE = 16 * 1024 ;
	
   
    


	public String execute() {
		
		List results = this.getDataCenterInterface().find(JuniorCollegeOnlineSignUpNews.class, content);
		if(results.size()<1)return "error";
		else {
			content = (JuniorCollegeOnlineSignUpNews) results.get(0);
			return "success";
		}
		
	}

	
	
	public JuniorCollegeOnlineSignUpNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeOnlineSignUpNews content) {
		this.content = content;
	}

}
