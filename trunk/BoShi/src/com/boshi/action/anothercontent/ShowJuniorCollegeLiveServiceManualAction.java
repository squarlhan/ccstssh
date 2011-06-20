package com.boshi.action.anothercontent;

import com.boshi.db.datamodel.anothercontent.JuniorCollegeLiveServiceManual;

public class ShowJuniorCollegeLiveServiceManualAction extends SuperShowAnotherContentAction {

	private static final long				serialVersionUID	= 1L;
	private JuniorCollegeLiveServiceManual	content				= new JuniorCollegeLiveServiceManual();

	public String execute() {
		Object obj = super.show(JuniorCollegeLiveServiceManual.class, "JuniorCollegeLiveServiceManual");
		if (obj != null) {
			content = (JuniorCollegeLiveServiceManual) obj;
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
		return super.change(JuniorCollegeLiveServiceManual.class, content);
	}

	public JuniorCollegeLiveServiceManual getContent() {
		return content;
	}

	public void setContent(JuniorCollegeLiveServiceManual content) {
		this.content = content;
	}

}
