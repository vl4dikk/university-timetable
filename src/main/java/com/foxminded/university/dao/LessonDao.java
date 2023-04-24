package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Lesson;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class LessonDao {

	private static final Logger logger = LoggerFactory.getLogger(LessonDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void insert(Lesson lesson) {
		if (lesson == null) {
			String error = "Cannot insert lesson, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		logger.trace("Start inserting lesson with name {}", lesson.getName());
		entityManager.persist(lesson);
		logger.trace("Lesson with name {} inserted", lesson.getName());
	}

	@Transactional
	public void deleteById(int lessonId) {
		logger.trace("Deleting lesson with id {}", lessonId);
		Lesson lesson = entityManager.find(Lesson.class, lessonId);
		entityManager.remove(lesson);
		logger.trace("Lesson with id {} deleted", lessonId);
	}

	public List<Lesson> getAllLessons() {
		logger.trace("Getting all lessons");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lesson> cq = cb.createQuery(Lesson.class);
		Root<Lesson> root = cq.from(Lesson.class);
		cq.select(root);
		TypedQuery<Lesson> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public Lesson getById(int lessonId) {
		logger.trace("Getting lesson with id {}", lessonId);
		Lesson result;
		try {
			result = entityManager.find(Lesson.class, lessonId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find lesson with id '%s'", lessonId);
			logger.error(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get lesson with ID '%s'", lessonId);
			logger.error(error);
			throw new DAOException(error, exception);
		}
		return result;
	}

	@Transactional
	public void update(Lesson lesson) {
		logger.trace("Updating lesson with id {}", lesson.getLessonId());
		entityManager.merge(lesson);
	}

}
