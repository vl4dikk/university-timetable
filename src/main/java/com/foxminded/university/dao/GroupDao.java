package com.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.university.configs.DaoConfig;
import com.foxminded.university.models.Group;

@Repository
public class GroupDao {

	private static final String DELETE_BY_ID_QUERY = "DELETE FROM groups WHERE id = ?";
	private static final String INSERT_GROUPS_QUERY = "INSERT INTO groups (name) VALUES (?)";

	private final JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Group> rowMapper = new BeanPropertyRowMapper<>(Group.class);

	@Autowired
	public GroupDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Group group) {
		jdbcTemplate.update(INSERT_GROUPS_QUERY, group.getName());
	}

	public void insert(List<Group> groups) {
		for (Group group : groups) {
			jdbcTemplate.update(INSERT_GROUPS_QUERY, group.getName());
		}
	}

	public void deleteById(int groupId) {
		jdbcTemplate.update(DELETE_BY_ID_QUERY, groupId);
	}

	public List<Group> getAllGroups() {
		String sql = "SELECT * FROM groups";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Group getGroupById(int groupId) {
		String sql = "SELECT * FROM groups WHERE id = ?";
		Group group = null;
		group = jdbcTemplate.queryForObject(sql, rowMapper, groupId);
		return group;
	}

}
