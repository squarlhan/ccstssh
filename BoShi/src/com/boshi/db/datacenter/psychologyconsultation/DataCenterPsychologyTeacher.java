package com.boshi.db.datacenter.psychologyconsultation;

import java.util.List;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import com.boshi.db.datacenter.SuperDateChanging;
import com.boshi.db.datamodel.psychologyconsultation.QuestionAndAnswer;

@Transactional
public class DataCenterPsychologyTeacher extends SuperDateChanging {
	// 心理咨询员回答问题
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, String param[]) {
		Query query = super.getEntityManager().createNamedQuery("QuestionAndAnswer.ListAllNotAnswer");
		return super.list(startNO, howManyOnePage, query);
	}

	@Transactional(readOnly = true)
	public long amount(String param[]) {
		Query query = super.getEntityManager().createNamedQuery("QuestionAndAnswer.AmountNotAnswer");
		return super.amount(query);
	}

	public void addAnswerObject(Object obj) {
		QuestionAndAnswer answer = (QuestionAndAnswer) obj;
		QuestionAndAnswer questionAndAnswer = (QuestionAndAnswer) super.findObject(QuestionAndAnswer.class, answer.getId());
		questionAndAnswer.setAContent(answer.getAContent());
		questionAndAnswer.setHaveAnswered(true);
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
