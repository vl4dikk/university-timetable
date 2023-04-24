package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class TeacherDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class);

	@PersistenceContext
	private EntityManager entityManager;;

	@Transactional
	public void insert(Teacher teacher) {
		logger.trace("Start inserting teacher");
		if (teacher == null) {
			String error = "Cannot insert teacher, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		entityManager.persist(teacher);;
		logger.trace("Teacher inserted");
	}

	@Transactional
	public void deleteById(int teacherId) {
		logger.trace("Deleting teacher with id {}", teacherId);
		Teacher teacher = entityManager.find(Teacher.class, teacherId);
		entityManager.remove(teacher);
	}

	public List<Teacher> getAllTeachers() {
		logger.trace("Getting all teachers");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
		Root<Teacher> root = cq.from(Teacher.class);
		cq.select(root);
		TypedQuery<Teacher> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public Teacher getTeacherById(int teacherId) {
		logger.trace("Getting teacher with id {}", teacherId);
		Teacher teacher;
		try {
			teacher = entityManager.find(Teacher.class, teacherId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find teacher with id '%s'", teacherId);
			logger.error(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get teacher with ID '%s'", teacherId);
			logger.error(error);
			throw new DAOException(error, exception);
		}
		return teacher;
	}
	
	@Transactional
	public void update(Teacher teacher) {
		logger.trace("Updating teacher with id {}", teacher.getTeacherId());
		entityManager.merge(teacher);
	}
}
