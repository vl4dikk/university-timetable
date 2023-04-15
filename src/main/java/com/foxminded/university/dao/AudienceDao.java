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
import com.foxminded.university.models.Audience;

@Repository
public class AudienceDao {

	private static final Logger logger = LoggerFactory.getLogger(AudienceDao.class);

	private final JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Audience> rowMapper = new BeanPropertyRowMapper<>(Audience.class);

	@Autowired
	public AudienceDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Audience audience) {
		if (audience == null) {
			String error = "Cannot insert audience, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		logger.trace("Start inserting audience with number {}", audience.getAudienceNumber());
		String sql = "INSERT INTO audiences (audienceNumber) VALUES (?)";
		jdbcTemplate.update(sql, audience.getAudienceNumber());
		logger.trace("Audience with number {} inserted", audience.getAudienceNumber());
	}

	public void deleteById(int audienceId) {
		logger.trace("Deleting audience with id {}", audienceId);
		String sql = "DELETE FROM audiences WHERE audienceId = ?";
		jdbcTemplate.update(sql, audienceId);
		logger.trace("Audience with id {} was deleted", audienceId);
	}

	public List<Audience> getAllAudiences() {
		logger.trace("Getting all audiences");
		String sql = "SELECT audienceId, audienceNumber FROM audiences";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Audience getAudienceById(int audienceId) {
		logger.trace("Getting audience with id {}", audienceId);
		String sql = "SELECT audienceId, audienceNumber FROM audiences WHERE audienceId = ?";
		Audience audience;
		try {
			audience = jdbcTemplate.queryForObject(sql, rowMapper, audienceId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find audience with id '%s'", audienceId);
			logger.error(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get audience with ID '%s'", audienceId);
			logger.error(error);
			throw new DAOException(error, exception);
		}
		return audience;
	}
	
	public void update(Audience audience) {
		logger.trace("Updating audience with id {}", audience.getAudienceId());
		String sql = "UPDATE audiences SET audienceNumber = ?  WHERE audienceId = ?";
		jdbcTemplate.update(sql, audience.getAudienceNumber(), audience.getAudienceNumber());
	}
}
