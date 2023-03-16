package com.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Teacher;

@Repository
public class TeacherDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	private BeanPropertyRowMapper<Teacher> rowMapper = new BeanPropertyRowMapper<>(Teacher.class);

	
	@Autowired
	public TeacherDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void insert(Teacher teacher) {
		String sql = "INSERT INTO teachers (firstname, lastname) VALUES (?, ?)";
		jdbcTemplate.update(sql, teacher.getFirstName(), teacher.getLastName());
	}

	public void insert(List<Teacher> teachers) {
		String sql = "INSERT INTO teachers (firstname, lastname) VALUES (?, ?)";
		for (Teacher teacher : teachers) {
			jdbcTemplate.update(sql, teacher.getFirstName(), teacher.getLastName());
		}
	}

	public void deleteById(int teacherId) {
		String sql = "DELETE FROM teachers WHERE teacherId = ?";
		jdbcTemplate.update(sql, teacherId);
	}

	public List<Teacher> getAllTeachers() {
		String sql = "SELECT * FROM teachers";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Teacher getTeacherById(int teacherId) {
		String sql = "SELECT * FROM teachers WHERE teacherId = ?";
		Teacher teacher = jdbcTemplate.queryForObject(sql, rowMapper, teacherId);
		return teacher;
	}
}
