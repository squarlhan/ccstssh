package com.boshi.db.datacenter.news;

import java.util.List;

import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.*;
import com.boshi.db.datamodel.news.ContentOfNews;
import com.boshi.db.datamodel.news.JuniorCollegeOnlineSignUpNews;

@Transactional(readOnly = true)
public class DataCenterJuniorCollegeOnlineSignUpNews extends SuperDateChanging {
	// 在线报名
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("JuniorCollegeOnlineSignUpNews.ListAll");
		return super.list(startNO, howManyOnePage, query);
	}

	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("JuniorCollegeOnlineSignUpNews.Amount");
		return super.amount(query);
	}
	
	public boolean checkdumplicate(String keyword){
		//System.out.println("SIZE: 11111");
		Query query = super.getEntityManager().createNamedQuery("JuniorCollegeOnlineSignUpNews.CheckDump");
		query.setParameter("examid", keyword);
		//System.out.println("SIZE: "+query.getResultList().size());
		if (query.getResultList().size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List getall() {
		// TODO Auto-generated method stub
		Query query = super.getEntityManager().createNamedQuery("JuniorCollegeOnlineSignUpNews.ListAll");
		return query.getResultList();
	}

	@Override
	public List<?> find(Class<?> classType, Object obj) {
		// TODO Auto-generated method stub
		Query query = super.getEntityManager().createNamedQuery("JuniorCollegeOnlineSignUpNews.find");
		query.setParameter("examid", ((JuniorCollegeOnlineSignUpNews)obj).getExamid());
		query.setParameter("pid", ((JuniorCollegeOnlineSignUpNews)obj).getPid());
		return query.getResultList();
	}
	
	public void changeContent(Class<?> classType, Object obj) {
		JuniorCollegeOnlineSignUpNews newContent=(JuniorCollegeOnlineSignUpNews)obj;
		JuniorCollegeOnlineSignUpNews oldCollegiate = (JuniorCollegeOnlineSignUpNews) this.findObject(classType, newContent.getId());
		oldCollegiate.setIsaccept(newContent.getIsaccept());
	}

	
}
