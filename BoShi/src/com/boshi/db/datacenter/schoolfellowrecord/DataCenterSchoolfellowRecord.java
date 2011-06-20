package com.boshi.db.datacenter.schoolfellowrecord;

import java.util.List;

import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.user.SchoolfellowUser;
import com.boshi.db.datamodel.schoolfellowrecord.RecordContent;

@Transactional
public class DataCenterSchoolfellowRecord extends SuperDateChanging {
	// 校友录
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("RecordContent.ListAll");
		return super.list(startNO, howManyOnePage, query);
	}

	@Transactional(readOnly = true)
	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("RecordContent.Amount");
		return super.amount(query);
	}

	public void removeObject(Class<?> classType, Object id) {
		RecordContent content = (RecordContent) super.findObject(classType, id);
		content.getSchoolfellowUser().getRecordContents().remove(content);
		content.setSchoolfellowUser(null);
		super.getEntityManager().remove(content);
	}

	public void addQuestionObject(Object obj, String genearchName) {
		RecordContent content = (RecordContent) obj;
		SchoolfellowUser schoolfellowRecordUser = null;
		if (content.getSchoolfellowUser().getName() == null) {
			schoolfellowRecordUser = (SchoolfellowUser) super.findObject(SchoolfellowUser.class, "匿名");
			if (schoolfellowRecordUser == null) {
				schoolfellowRecordUser = new SchoolfellowUser("匿名", "匿名匿名");
				super.createObject(schoolfellowRecordUser);
			}
		} else {
			schoolfellowRecordUser = (SchoolfellowUser) super.findObject(SchoolfellowUser.class, content.getSchoolfellowUser()
					.getName());
		}
		content.setSchoolfellowUser(schoolfellowRecordUser);
		schoolfellowRecordUser.getRecordContents().add(content);
		super.createObject(content);
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
