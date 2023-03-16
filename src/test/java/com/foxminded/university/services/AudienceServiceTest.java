package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.university.dao.AudienceDao;
import com.foxminded.university.models.Audience;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AudienceServiceTest {

	@Mock
	AudienceDao dao;

	@InjectMocks
	AudienceService service;

	@Test
	void testInsertInt() {
		service.insert(anyInt());
		verify(dao, times(1)).insert(any(Audience.class));
	}

	@Test
	void testInsertListOfInteger() {
		service.insert(anyList());
		verify(dao, times(1)).insert(anyList());
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllAudiences() {
		service.getAllAudiences();
		verify(dao, times(1)).getAllAudiences();
	}

	@Test
	void testGetAudienceById() {
		service.getAudienceById(anyInt());
		verify(dao, times(1)).getAudienceById(anyInt());
	}

}
