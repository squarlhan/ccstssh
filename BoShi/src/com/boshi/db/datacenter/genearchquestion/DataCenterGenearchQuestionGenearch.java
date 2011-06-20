package com.boshi.db.datacenter.genearchquestion;

import java.util.List;

import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.genearchquestion.TheClass;
import com.boshi.db.datamodel.genearchquestion.GenearchQuestion;
import com.boshi.db.datamodel.user.GenearchUser;

@Transactional
public class DataCenterGenearchQuestionGenearch extends SuperDateChanging {
	// 班级家长问题
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("GenearchQuestion.ListAllTheGenearch");
		query.setParameter("name", param[0]);
		return super.list(startNO, howManyOnePage, query);
	}

	@Transactional(readOnly = true)
	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("GenearchQuestion.AmountTheGenearch");
		query.setParameter("name", param[0]);
		return super.amount(query);
	}

	public void addQuestionObject(Object obj, String genearchName) {
		GenearchQuestion question = (GenearchQuestion) obj;
		GenearchUser genearchUser = (GenearchUser) super.findObject(GenearchUser.class, genearchName);
		genearchUser.getGenearchQuestion().add(question);
		question.setGenearchUser(genearchUser);
		super.createObject(question);
	}

	@Transactional(readOnly = true)
	public List<?> list() {
		Query query = super.getEntityManager().createNamedQuery("TheClass.ListAll");
		return super.list(query);
	}

	public void createObject(Object obj) {
		GenearchUser user = (GenearchUser) obj;
		TheClass theClass = (TheClass) super.findObject(TheClass.class, user.getTheClass().getName());
		theClass.getGenearchUser().add(user);
		user.setTheClass(theClass);
		super.createObject(user);
	}

	@Override
	public boolean checkdumplicate(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List getall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> find(Class<?> classType, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
