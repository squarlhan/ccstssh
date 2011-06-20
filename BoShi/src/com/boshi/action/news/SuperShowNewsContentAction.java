package com.boshi.action.news;

import org.apache.struts2.interceptor.ServletRequestAware;
import com.boshi.action.SuperShowContentAction;
import com.boshi.db.datamodel.news.ContentOfNews;

public class SuperShowNewsContentAction extends SuperShowContentAction implements ServletRequestAware {

	private static final long	serialVersionUID	= 1L;

	public Object show(Class<?> classType) {
		String id = (String) servletRequest.getParameter("id");
		if (id == null)
			return null;
		return super.show(classType, Long.valueOf(id));
	}

	public String change(Class<?> classType, ContentOfNews content) {
		return super.change(classType, content);
	}

}
