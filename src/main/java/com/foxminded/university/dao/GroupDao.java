package com.foxminded.university.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import com.foxminded.university.models.Group;

@Component
public class GroupDao {

	private static final String DELETE_BY_ID_QUERY = "DELETE FROM groups WHERE group_id = ?";
	private static final String INSERT_GROUPS_QUERY = "INSERT INTO groups (group_name) VALUES (?)";

	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Group> rowMapper = (rs, rowNum) -> {
		Group group = new Group();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("group_name"));
		return group;
	};

	public GroupDao() {
		this.dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/university");
		dataSource.setUsername("postgres");
		dataSource.setPassword("453");
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
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

	@SuppressWarnings("deprecation")
	public Group getGroupById (int groupId) {
		String sql = "SELECT * FROM groups WHERE group_id = ?";
		Object[] params = new Object[] {groupId};
		Group group = null;
		group = jdbcTemplate.queryForObject(sql, params,rowMapper);
		return group;
	}

}
