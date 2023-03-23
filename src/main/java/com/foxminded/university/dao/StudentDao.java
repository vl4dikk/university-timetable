package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;

@Repository
public class StudentDao {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Student student) {
		logger.trace("Start inserting student");
		if (student == null) {
			String error = "Cannot insert student, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		String sql = "INSERT INTO students (firstName, lastName) VALUES (?, ?)";
		jdbcTemplate.update(sql, student.getFirstName(), student.getLastName());
		logger.trace("Student inserted");
	}

	public void deleteById(int studentId) {
		logger.trace("Deleting student with id {}", studentId);
		String sql = "DELETE FROM students WHERE studentId = ?";
		jdbcTemplate.update(sql, studentId);
	}

	public List<Student> getAllStudents() {
		logger.trace("Getting all students");
		String sql = "SELECT s.studentId, s.firstName, s.lastName, g.id, g.name " + "FROM students s  "
				+ "LEFT JOIN groups g  ON s.group_id = g.id";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Student student = new Student();
			student.setStudentId(rs.getInt("studentId"));
			student.setFirstName(rs.getString("firstName"));
			student.setLastName(rs.getString("lastName"));
			Group group = new Group();
			group.setId(rs.getInt("id"));
			group.setName(rs.getString("name"));
			student.setGroup(group);
			return student;
		});
	}

	public Student getById(int studentId) {
		logger.trace("Getting student with id {}", studentId);
		String sql = "SELECT s.studentId, s.firstName, s.lastName, g.id, g.name " + "FROM students s "
				+ "LEFT JOIN groups g ON s.group_id = g.id " + "WHERE studentId = ?";
		Student result;
		RowMapper<Student> rowMapper = (rs, rowNum) -> {
			Student student = new Student();
			student.setStudentId(rs.getInt("studentId"));
			student.setFirstName(rs.getString("firstName"));
			student.setLastName(rs.getString("lastName"));
			Group group = new Group();
			group.setId(rs.getInt("id"));
			group.setName(rs.getString("name"));
			student.setGroup(group);
			return student;
		};
		try {
			result = jdbcTemplate.queryForObject(sql, rowMapper, studentId);
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

	public void assignStudentToGroup(int studentId, int groupId) {
		logger.trace("Assigning student with id {}, to group with id{}", studentId, groupId);
		String sql = "UPDATE students SET group_id = ? WHERE studentId = ?";
		jdbcTemplate.update(sql, groupId, studentId);
	}

}
