package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Teacher;

@Repository
public class TeacherDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class);

	private final JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Teacher> rowMapper = new BeanPropertyRowMapper<>(Teacher.class);

	@Autowired
	public TeacherDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Teacher teacher) {
		logger.debug("Start inserting teacher");
		if (teacher == null) {
			String error = "Cannot insert teacher, because its null";
			logger.warn(error);
			throw new DAOException(error);
		}
		String sql = "INSERT INTO teachers (firstname, lastname) VALUES (?, ?)";
		jdbcTemplate.update(sql, teacher.getFirstName(), teacher.getLastName());
		logger.debug("Teacher inserted");
	}

	public void deleteById(int teacherId) {
		logger.debug("Deleting teacher with id {}", teacherId);
		String sql = "DELETE FROM teachers WHERE teacherId = ?";
		jdbcTemplate.update(sql, teacherId);
	}

	public List<Teacher> getAllTeachers() {
		logger.debug("Getting all teachers");
		String sql = "SELECT teacherId, firstName, lastName FROM teachers";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Teacher getTeacherById(int teacherId) {
		logger.debug("Getting teacher with id {}", teacherId);
		String sql = "SELECT teacherId, firstName, lastName FROM teachers WHERE teacherId = ?";
		Teacher teacher;
		try {
			teacher = jdbcTemplate.queryForObject(sql, rowMapper, teacherId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find teacher with id '%s'", teacherId);
			logger.warn(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get teacher with ID '%s'", teacherId);
			logger.warn(error);
			throw new DAOException(error, exception);
		}
		return teacher;
	}
}
