package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class StudentDao {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void insert(Student student) {
		logger.trace("Start inserting student");
		if (student == null) {
			String error = "Cannot insert student, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		entityManager.persist(student);
		logger.trace("Student inserted");
	}
	
	@Transactional
	public void deleteById(int studentId) {
		logger.trace("Deleting student with id {}", studentId);
		Student student = entityManager.find(Student.class, studentId);
		entityManager.remove(student);
	}

	public List<Student> getAllStudents() {
		logger.trace("Getting all students");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root);
		TypedQuery<Student> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public Student getById(int studentId) {
		logger.trace("Getting student with id {}", studentId);
		Student result;
		try {
			result = entityManager.find(Student.class, studentId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find student with id '%s'", studentId);
			logger.error(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get student with ID '%s'", studentId);
			logger.error(error);
			throw new DAOException(error, exception);
		}
		return result;
	}
	
	@Transactional
	public void update(Student student) {
		logger.trace("Updating student with id {}", student.getStudentId());
		entityManager.merge(student);
	}

}
