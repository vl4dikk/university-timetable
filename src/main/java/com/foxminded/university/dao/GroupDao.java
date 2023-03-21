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
import com.foxminded.university.models.Group;

@Repository
public class GroupDao {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupDao.class);

	private final JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Group> rowMapper = new BeanPropertyRowMapper<>(Group.class);

	@Autowired
	public GroupDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Group group) {
		logger.debug("Start inserting group");
		if (group == null) {
			String error = "Cannot insert group, because its null";
			logger.warn(error);
			throw new DAOException(error);
		}
		String sql = "INSERT INTO groups (name) VALUES (?)";
		jdbcTemplate.update(sql, group.getName());
		logger.debug("Group inserted");
	}

	public void deleteById(int groupId) {
		logger.debug("Deleting group with id {}", groupId);
		String sql = "DELETE FROM groups WHERE id = ?";
		jdbcTemplate.update(sql, groupId);
	}

	public List<Group> getAllGroups() {
		logger.debug("Getting all groups");
		String sql = "SELECT id, name FROM groups";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Group getGroupById(int groupId) {
		logger.debug("Getting group with id {}", groupId);
		String sql = "SELECT id, name FROM groups WHERE id = ?";
		Group group;
		try {
			group = jdbcTemplate.queryForObject(sql, rowMapper, groupId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find group with id '%s'", groupId);
			logger.warn(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get group with ID '%s'", groupId);
			logger.warn(error);
			throw new DAOException(error, exception);
		}
		return group;
	}

}
