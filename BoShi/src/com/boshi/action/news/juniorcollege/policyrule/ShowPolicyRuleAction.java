package com.boshi.action.news.juniorcollege.policyrule;

import com.boshi.action.news.SuperShowNewsContentAction;
import com.boshi.db.datamodel.news.JuniorCollegePolicyRulee;

public class ShowPolicyRuleAction extends SuperShowNewsContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegePolicyRulee	content				= new JuniorCollegePolicyRulee();

	public String execute() {
		Object obj = super.show(JuniorCollegePolicyRulee.class);
		if (obj != null) {
			content = (JuniorCollegePolicyRulee) obj;
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
		return super.change(JuniorCollegePolicyRulee.class, content);
	}

	public JuniorCollegePolicyRulee getContent() {
		return content;
	}

	public void setContent(JuniorCollegePolicyRulee content) {
		this.content = content;
	}
}
