package com.foxminded.university.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.foxminded.university.configs.DaoConfig;
import com.foxminded.university.models.Group;

import jakarta.annotation.PostConstruct;

@Repository
@Import(DaoConfig.class)
public class GroupDao {

	private static final String DELETE_BY_ID_QUERY = "DELETE FROM groups WHERE group_id = ?";
	private static final String INSERT_GROUPS_QUERY = "INSERT INTO groups (group_name) VALUES (?)";

//	@Autowired
	private final JdbcTemplate jdbcTemplate;

	private RowMapper<Group> rowMapper = (rs, rowNum) -> {
		Group group = new Group();
		group.setId(rs.getInt("group_id"));
		group.setName(rs.getString("group_name"));
		return group;
	};
	
	@Autowired
	public GroupDao (JdbcTemplate jdbcTemplate) {
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

	@SuppressWarnings("deprecation")
	public Group getGroupById(int groupId) {
		String sql = "SELECT * FROM groups WHERE group_id = ?";
		Object[] params = new Object[] { groupId };
		Group group = null;
		group = jdbcTemplate.queryForObject(sql, params, rowMapper);
		return group;
	}

}
