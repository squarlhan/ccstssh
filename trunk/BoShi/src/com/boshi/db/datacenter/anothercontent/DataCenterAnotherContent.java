package com.boshi.db.datacenter.anothercontent;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.anothercontent.ContentOfAnother;

@Transactional
public class DataCenterAnotherContent extends SuperDateChanging {
	// 其他内容
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		return null;
	}

	@Transactional(readOnly = true)
	public long amount(String param[]) {
		return -1;
	}

	public Object findObject(Class<?> classType, Object id) {
		Object obj = super.findObject(classType, id);
		if (obj == null) {
			try {
				obj = classType.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.createObject(obj);
		}
		return obj;
	}

	public void changeContent(Class<?> classType, Object obj) {
		ContentOfAnother newContent = (ContentOfAnother) obj;
		ContentOfAnother oldContent = (ContentOfAnother) this.findObject(classType, newContent.getId());
		oldContent.setContent(newContent.getContent());
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
