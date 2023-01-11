package com.foxminded.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.university.models.Student;

public class StudentDao {

	private static final String GET_ALL_STUDENTS_QUERY = "SELECT * FROM students";
	private static final String DELETE_BY_ID_QUERY = "DELETE FROM students WHERE student_id = ?";
	private static final String INSERT_STUDENTS_QUERY = "INSERT INTO students (first_name, last_name, group) "
			+ "     VALUES (?, ?, ?)";

	private JdbcTemplate jdbcTemplate;

	public StudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Student student) {
		jdbcTemplate.update(INSERT_STUDENTS_QUERY, student.getFirstName(), student.getLastName(), student.getGroup());
	}

	public void insert(List<Student> students) {
		for (Student student : students) {
			jdbcTemplate.update(INSERT_STUDENTS_QUERY, student.getFirstName(), student.getLastName(),
					student.getGroup());
		}
	}
	
	public void deleteById (int studentId) {
		jdbcTemplate.update(DELETE_BY_ID_QUERY, studentId);
	}
	
	public List<Student> getAllStudents (){
		return jdbcTemplate.query(GET_ALL_STUDENTS_QUERY, new StudentRawMapper());
	}

}
