package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;

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
		List<Integer> test = new LinkedList<>();
		test.add(3);
		test.add(5);
		service.insert(test);
		verify(dao, times(2)).insert(any(Audience.class));
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
