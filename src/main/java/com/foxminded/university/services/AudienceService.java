package com.foxminded.university.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.AudienceDao;
import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Audience;

@Service
public class AudienceService {

	private static final Logger logger = LoggerFactory.getLogger(AudienceService.class);

	private AudienceDao dao;

	@Autowired
	public AudienceService(AudienceDao dao) {
		this.dao = dao;
	}

	public void insert(int audienceNumber) {
		logger.debug("Started AudienceService to insert audience");
		Audience audience = new Audience();
		audience.setAudienceNumber(audienceNumber);
		try {
			dao.insert(audience);
			logger.debug("Audience inserted");
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}

	public void insert(List<Integer> audienceNumbers) {
		logger.debug("Started AudienceService to insert list of audiences");
		for (int audienceNumber : audienceNumbers) {
			Audience audience = new Audience();
			audience.setAudienceNumber(audienceNumber);
			try {
				dao.insert(audience);
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void deleteById(int audienceId) {
		logger.debug("Started AudienceService to delete audience by id {}", audienceId);
		dao.deleteById(audienceId);
	}

	public List<Audience> getAllAudiences() {
		logger.debug("Started AudienceService to get all audiences");
		return dao.getAllAudiences();
	}

	public Audience getAudienceById(int audienceId) {
		logger.debug("Started AudienceService to get audience by id {}", audienceId);
		Audience audience = new Audience();
		try {
			audience = dao.getAudienceById(audienceId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return audience;
	}
}
