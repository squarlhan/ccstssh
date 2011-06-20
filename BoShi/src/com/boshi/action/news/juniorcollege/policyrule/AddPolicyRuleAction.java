package com.boshi.action.news.juniorcollege.policyrule;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegePolicyRulee;

public class AddPolicyRuleAction extends SuperAddNewsAction{

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegePolicyRulee	content				= new JuniorCollegePolicyRulee();

	public String execute() {
		return super.add(content);
	}

	public JuniorCollegePolicyRulee getContent() {
		return content;
	}

	public void setContent(JuniorCollegePolicyRulee content) {
		this.content = content;
	}
}
