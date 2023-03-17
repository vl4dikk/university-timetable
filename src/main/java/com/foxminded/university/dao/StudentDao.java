package com.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;

@Repository
public class StudentDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Student student) {
		String sql = "INSERT INTO students (firstName, lastName) VALUES (?, ?)";
		jdbcTemplate.update(sql, student.getFirstName(), student.getLastName());
	}

	public void deleteById(int studentId) {
		String sql = "DELETE FROM students WHERE studentId = ?";
		jdbcTemplate.update(sql, studentId);
	}

	public List<Student> getAllStudents() {
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
		String sql = "SELECT s.studentId, s.firstName, s.lastName, g.id, g.name " + "FROM students s "
				+ "LEFT JOIN groups g ON s.group_id = g.id " + "WHERE studentId = ?";
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
		return jdbcTemplate.queryForObject(sql, rowMapper, studentId);
	}

	public void assignStudentToGroup(int studentId, int groupId) {
		String sql = "UPDATE students SET group_id = ? WHERE studentId = ?";
		jdbcTemplate.update(sql, groupId, studentId);
	}

}
