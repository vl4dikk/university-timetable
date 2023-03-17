package com.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Group;

@Repository
public class GroupDao {

	private final JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Group> rowMapper = new BeanPropertyRowMapper<>(Group.class);

	@Autowired
	public GroupDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Group group) {
		String sql = "INSERT INTO groups (name) VALUES (?)";
		jdbcTemplate.update(sql, group.getName());
	}

	public void deleteById(int groupId) {
		String sql = "DELETE FROM groups WHERE id = ?";
		jdbcTemplate.update(sql, groupId);
	}

	public void deleteAll() {
		String sql = "DELETE FROM groups";
		jdbcTemplate.update(sql);
	}

	public List<Group> getAllGroups() {
		String sql = "SELECT id, name FROM groups";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Group getGroupById(int groupId) {
		String sql = "SELECT id, name FROM groups WHERE id = ?";
		Group group = jdbcTemplate.queryForObject(sql, rowMapper, groupId);
		return group;
	}

}
