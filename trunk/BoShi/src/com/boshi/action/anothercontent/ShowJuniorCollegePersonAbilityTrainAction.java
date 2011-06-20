package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegePersonAbilityTrain;

public class ShowJuniorCollegePersonAbilityTrainAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegePersonAbilityTrain	content				= new JuniorCollegePersonAbilityTrain();

	public String execute() {
		Object obj = super.show(JuniorCollegePersonAbilityTrain.class, "JuniorCollegePersonAbilityTrain");
		if (obj != null) {
			content = (JuniorCollegePersonAbilityTrain) obj;
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
		return super.change(JuniorCollegePersonAbilityTrain.class, content);
	}

	public JuniorCollegePersonAbilityTrain getContent() {
		return content;
	}

	public void setContent(JuniorCollegePersonAbilityTrain content) {
		this.content = content;
	}

}
