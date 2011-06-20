package com.boshi.action.anothercontent;

import org.apache.struts2.interceptor.ServletRequestAware;
import com.boshi.action.SuperShowContentAction;
import com.boshi.db.datamodel.anothercontent.ContentOfAnother;

public class SuperShowAnotherContentAction extends SuperShowContentAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;

	public Object show(Class<?> classType, String id) {
		return super.show(classType, id);
	}

	public String change(Class<?> classType, ContentOfAnother content) {
		return super.change(classType, content);
	}

}
