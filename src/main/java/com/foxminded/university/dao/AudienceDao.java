package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Audience;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class AudienceDao {

	private static final Logger logger = LoggerFactory.getLogger(AudienceDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void insert(Audience audience) {
		logger.trace("Start inserting audience with number {}", audience.getAudienceNumber());
		entityManager.persist(audience);
		logger.trace("Audience with number {} inserted", audience.getAudienceNumber());
	}

	@Transactional
	public void deleteById(int audienceId) {
		logger.trace("Deleting audience with id {}", audienceId);
		Audience audience = entityManager.find(Audience.class, audienceId);
		if (audience != null) {
			entityManager.remove(audience);
			logger.trace("Audience with id {} was deleted", audienceId);
		}
	}

	public List<Audience> getAllAudiences() {
		logger.trace("Getting all audiences");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Audience> cq = cb.createQuery(Audience.class);
		Root<Audience> root = cq.from(Audience.class);
		cq.select(root);
		TypedQuery<Audience> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public Audience getAudienceById(int audienceId) {
		logger.trace("Getting audience with id {}", audienceId);
		Audience audience;
		try {
			audience = entityManager.find(Audience.class, audienceId);
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

	@Transactional
	public void update(Audience audience) {
		logger.trace("Updating audience with id {}", audience.getAudienceId());
		entityManager.merge(audience);
	}
}
