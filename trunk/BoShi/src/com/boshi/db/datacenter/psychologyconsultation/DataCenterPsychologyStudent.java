package com.boshi.db.datacenter.psychologyconsultation;

import java.util.List;

import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.psychologyconsultation.QuestionAndAnswer;
import com.boshi.db.datamodel.user.StudentUser;

@Transactional
public class DataCenterPsychologyStudent extends SuperDateChanging {
	// 学生心理问题
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("QuestionAndAnswer.ListAllTheStudent");
		query.setParameter("name", param[0]);
		return super.list(startNO, howManyOnePage, query);
	}

	@Transactional(readOnly = true)
	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("QuestionAndAnswer.AmountTheStudent");
		query.setParameter("name", param[0]);
		return super.amount(query);
	}

	public void addQuestionObject(Object obj, String studentName) {
		QuestionAndAnswer question = (QuestionAndAnswer) obj;
		StudentUser studentUser = (StudentUser) super.findObject(StudentUser.class, studentName);
		studentUser.getQuestionAndAnswers().add(question);
		question.setStudentUser(studentUser);
		super.createObject(question);
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
