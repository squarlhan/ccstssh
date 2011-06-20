package com.boshi.db.datacenter;

import java.util.List;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import com.boshi.db.datamodel.news.ContentOfNews;

@Transactional
public abstract class SuperDateChanging implements DataCenterInterface {
	private EntityManager	entityManager;

	@Transactional(readOnly = true)
	public List<?> list(Query query) {
		List<?> list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Object> list(int startNO, int howManyOnePage, Query query) {
		query.setMaxResults(howManyOnePage);
		query.setFirstResult(startNO);
		List<Object> list = query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public long amount(Query query) {
		long amount = 0;
		try {
			amount = (Long) query.getSingleResult();
		} catch (Exception ex) {
			amount = 0;
		}
		return amount;
	}

	@Transactional(readOnly = true)
	public Object findObject(Class<?> classType, Object id) {
		return entityManager.find(classType, id);
	}

	public void createObject(Object obj) {
		entityManager.persist(obj);
	}

	public void changeContent(Class<?> classType, Object obj) {
		ContentOfNews newContent=(ContentOfNews)obj;
		ContentOfNews oldCollegiate = (ContentOfNews) this.findObject(classType, newContent.getId());
		oldCollegiate.setTitle(newContent.getTitle());
		oldCollegiate.setContent(newContent.getContent());
	}

	public void removeObject(Class<?> classType, Object id) {
		entityManager.remove(entityManager.find(classType, id));
	}

	@Transactional(readOnly = true)
	public List<?> list() {
		return null;
	}

	public void addQuestionObject(Object answer, String id) {
	}

	public void addAnswerObject(Object answer) {
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
