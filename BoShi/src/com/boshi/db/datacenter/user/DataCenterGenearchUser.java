package com.boshi.db.datacenter.user;

import java.util.List;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import com.boshi.db.datamodel.user.GenearchUser;
import com.boshi.db.datacenter.*;

@Transactional(readOnly = true)
public class DataCenterGenearchUser extends SuperDateChanging {
	// 家长
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("GenearchUser.ListAll");
		return super.list(startNO, howManyOnePage, query);
	}

	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("GenearchUser.Amount");
		return super.amount(query);
	}

	@Transactional(readOnly = false)
	public void removeObject(Class<?> classType, Object id) {
		GenearchUser genearch = (GenearchUser) super.findObject(classType, id);
		genearch.getTheClass().getGenearchUser().remove(genearch);
		genearch.setTheClass(null);
		super.getEntityManager().remove(genearch);
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
