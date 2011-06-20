package com.boshi.interceptor.accesscontrol;

import com.boshi.action.SuperLoginAndRegisterAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class JuniorHighSchoolAdminAccessInterceptor implements Interceptor {

	private static final long	serialVersionUID	= 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if (actionInvocation.getAction() instanceof SuperLoginAndRegisterAction)
			return actionInvocation.invoke();
		ActionContext context = actionInvocation.getInvocationContext();
		String name = (String) context.getSession().get("name");
		String power = (String) context.getSession().get("power");
		if (name != null && power != null) {
			if (power.equals("highSchoolAdmin") || power.equals("admin")) {
				System.out.println("he is a highSchoolAdmin!!!!!!!!!!!!!!");
				return actionInvocation.invoke();
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
