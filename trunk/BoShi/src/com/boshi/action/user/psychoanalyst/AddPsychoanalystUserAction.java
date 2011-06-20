package com.boshi.action.user.psychoanalyst;

import com.boshi.db.datamodel.user.PsychoanalystUser;
import com.boshi.action.user.SuperAddUserAction;

public class AddPsychoanalystUserAction extends SuperAddUserAction {

	private static final long	serialVersionUID	= 1L;
	private PsychoanalystUser	user				= new PsychoanalystUser();

	public String execute() {
		return super.add(user);
	}

	public PsychoanalystUser getUser() {
		return user;
	}

	public void setUser(PsychoanalystUser user) {
		this.user = user;
	}

}
