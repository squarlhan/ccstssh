package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegePolicyRule;

public class ShowJuniorCollegePolicyRuleAction extends SuperShowAnotherContentAction {

	private static final long		serialVersionUID	= 1L;
	private JuniorCollegePolicyRule	content				= new JuniorCollegePolicyRule();

	public String execute() {
		Object obj = super.show(JuniorCollegePolicyRule.class, "JuniorCollegePolicyRule");
		if (obj != null) {
			content = (JuniorCollegePolicyRule) obj;
			return SUCCESS;
		}
		return ERROR;
	}

	public String toChange() {
		if (execute().equals(SUCCESS))
			return "toChange";
		return ERROR;
	}

	public String change() {
		return super.change(JuniorCollegePolicyRule.class, content);
	}

	public JuniorCollegePolicyRule getContent() {
		return content;
	}

	public void setContent(JuniorCollegePolicyRule content) {
		this.content = content;
	}

}
