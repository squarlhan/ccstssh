package com.boshi.db.datacenter.genearchquestion;

import java.util.List;

import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.genearchquestion.TheClass;

@Transactional(readOnly = true)
public class DataCenterTheClass extends SuperDateChanging {
	// 班级
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("TheClass.ListAll");
		return super.list(startNO, howManyOnePage, query);
	}

	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("TheClass.Amount");
		return super.amount(query);
	}

	@Transactional(readOnly = false)
	public void removeObject(Class<?> classType, Object id) {
		TheClass theClass = (TheClass) super.findObject(classType, id);
		theClass.getTeacherUser().getTheClasses().remove(theClass);
		theClass.setTeacherUser(null);
		super.getEntityManager().remove(theClass);
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
