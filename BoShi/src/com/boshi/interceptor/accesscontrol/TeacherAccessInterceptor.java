package com.boshi.interceptor.accesscontrol;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class TeacherAccessInterceptor implements Interceptor {

	private static final long	serialVersionUID	= 1L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		if (arg0.getAction() instanceof SuperLoginAndRegisterAction)
			return arg0.invoke();
		ActionContext context = arg0.getInvocationContext();
		String name = (String) context.getSession().get("name");
		String power = (String) context.getSession().get("power");
		if (name != null && power != null) {
			if (power.equals("teacher") || power.equals("admin")) {
				System.out.println("he is a teacher!!!!!!!!!!!!!!");
				String re = arg0.invoke();
				return re;
			}
		}
		return "pleaseToLogin";
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}
}
