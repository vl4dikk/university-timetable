package com.foxminded.university.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.AudienceDao;
import com.foxminded.university.models.Audience;

@Service
public class AudienceService {

	private AudienceDao dao;

	@Autowired
	public AudienceService(AudienceDao dao) {
		this.dao = dao;
	}

	public void insert(int audienceNumber) {
		Audience audience = new Audience();
		audience.setAudienceNumber(audienceNumber);
		dao.insert(audience);
	}

	public void insert(List<Integer> audienceNumbers) {
		for (int audienceNumber : audienceNumbers) {
			Audience audience = new Audience();
			audience.setAudienceNumber(audienceNumber);
			dao.insert(audience);
		}
	}

	public void deleteById(int audienceId) {
		dao.deleteById(audienceId);
	}

	public List<Audience> getAllAudiences() {
		return dao.getAllAudiences();
	}

	public Audience getAudienceById(int audienceId) {
		return dao.getAudienceById(audienceId);
	}
}
