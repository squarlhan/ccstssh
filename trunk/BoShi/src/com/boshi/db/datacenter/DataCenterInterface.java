package com.boshi.db.datacenter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public interface DataCenterInterface {

	public abstract Object findObject(Class<?> classType, Object id);

	public abstract void createObject(Object obj);

	public abstract void changeContent(Class<?> classType, Object obj);

	public abstract void removeObject(Class<?> classType, Object id);
	
	public boolean checkdumplicate(String keyword);
	
	public List<?> getall();

	public List<?> list();
	
	public List<?> find(Class<?> classType, Object obj);

	public abstract List<Object> list(int startNO, int howManyOnePage, String param[]);

	public abstract long amount(String param[]);

	public void addQuestionObject(Object answer, String id);

	public void addAnswerObject(Object answer);

	@PersistenceContext
	public abstract void setEntityManager(EntityManager entityManager);

}