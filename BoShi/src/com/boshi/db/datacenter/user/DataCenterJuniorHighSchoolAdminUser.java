package com.boshi.db.datacenter.user;

import java.util.List;

import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.*;

@Transactional(readOnly = true)
public class DataCenterJuniorHighSchoolAdminUser extends SuperDateChanging {
	// 初中管理员
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("JuniorHighSchoolAdminUser.ListAll");
		return super.list(startNO, howManyOnePage, query);
	}

	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("JuniorHighSchoolAdminUser.Amount");
		return super.amount(query);
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
