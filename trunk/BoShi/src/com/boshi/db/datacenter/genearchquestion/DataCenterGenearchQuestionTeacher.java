package com.boshi.db.datacenter.genearchquestion;

import java.util.List;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.genearchquestion.GenearchQuestion;
import com.boshi.db.datamodel.genearchquestion.TheClass;
import com.boshi.db.datamodel.user.TeacherUser;

@Transactional
public class DataCenterGenearchQuestionTeacher extends SuperDateChanging {
	// 老师回答班级家长问题
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("GenearchQuestion.ListAllNotAnswer");
		query.setParameter("name", param[0]);
		return super.list(startNO, howManyOnePage, query);
	}

	@Transactional(readOnly = true)
	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("GenearchQuestion.AmountAllNotAnswer");
		query.setParameter("name", param[0]);
		return super.amount(query);
	}

	public void addAnswerObject(Object obj) {
		GenearchQuestion answer = (GenearchQuestion) obj;
		GenearchQuestion questionAndAnswer = (GenearchQuestion) super.findObject(GenearchQuestion.class, answer.getId());
		questionAndAnswer.setAContent(answer.getAContent());
		questionAndAnswer.setHaveAnswered(true);
	}

	public void createObject(Object obj) {
		TheClass theClass = (TheClass) obj;
		TeacherUser teacher = (TeacherUser) super.findObject(TeacherUser.class, theClass.getTeacherUser().getName());
		teacher.getTheClasses().add(theClass);
		theClass.setTeacherUser(teacher);
		super.createObject(theClass);
	}

	@Override
	public boolean checkdumplicate(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List getall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> find(Class<?> classType, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
