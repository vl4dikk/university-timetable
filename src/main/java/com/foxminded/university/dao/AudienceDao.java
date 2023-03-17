package com.foxminded.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Audience;

@Repository
public class AudienceDao {

	private final JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Audience> rowMapper = new BeanPropertyRowMapper<>(Audience.class);

	@Autowired
	public AudienceDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Audience audience) {
		String sql = "INSERT INTO audiences (audienceNumber) VALUES (?)";
		jdbcTemplate.update(sql, audience.getAudienceNumber());
	}

	public void deleteById(int audienceId) {
		String sql = "DELETE FROM audiences WHERE audienceId = ?";
		jdbcTemplate.update(sql, audienceId);
	}

	public List<Audience> getAllAudiences() {
		String sql = "SELECT audienceId, audienceNumber FROM audiences";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public Audience getAudienceById(int audienceId) {
		String sql = "SELECT audienceId, audienceNumber FROM audiences WHERE audienceId = ?";
		Audience audience = jdbcTemplate.queryForObject(sql, rowMapper, audienceId);
		return audience;
	}
}
