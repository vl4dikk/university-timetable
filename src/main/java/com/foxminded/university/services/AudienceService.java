package com.foxminded.university.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.dao.AudienceDao;
import com.foxminded.university.models.Audience;

@Service
@Transactional
public class AudienceService {

	private static final Logger logger = LoggerFactory.getLogger(AudienceService.class);

	private AudienceDao dao;

	@Autowired
	public AudienceService(AudienceDao dao) {
		this.dao = dao;
	}

	public void insert(int audienceNumber) {
		logger.info("Started AudienceService to insert audience");
		Audience audience = new Audience();
		audience.setAudienceNumber(audienceNumber);
		dao.save(audience);
		logger.info("Audience inserted");
	}

	public void insert(List<Integer> audienceNumbers) {
		logger.info("Started AudienceService to insert list of audiences");
		for (int audienceNumber : audienceNumbers) {
			Audience audience = new Audience();
			audience.setAudienceNumber(audienceNumber);
			dao.save(audience);
		}
	}

	public void deleteById(int audienceId) {
		logger.info("Started AudienceService to delete audience by id {}", audienceId);
		dao.deleteById(audienceId);
	}

	public List<Audience> getAllAudiences() {
		logger.info("Started AudienceService to get all audiences");
		return dao.findAll();
	}

	public Audience getAudienceById(int audienceId) {
		logger.info("Started AudienceService to get audience by id {}", audienceId);
		return dao.getReferenceById(audienceId);
	}

	public void update(Audience audience) {
		dao.save(audience);
	}
}
