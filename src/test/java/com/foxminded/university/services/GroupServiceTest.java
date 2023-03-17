package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock
	GroupDao dao;

	@InjectMocks
	GroupService service;

	@Test
	void testInsert() {
		service.insert(anyString());
		verify(dao, times(1)).insert(any(Group.class));
	}

	@Test
	void testInsertListOfString() {
		List<String> test = new LinkedList<>();
		test.add("123");
		test.add("321");
		service.insert(test);
		verify(dao, times(2)).insert(any(Group.class));
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllGroups() {
		service.getAllGroups();
		verify(dao, times(1)).getAllGroups();
	}

	@Test
	void testGetGroupById() {
		service.getGroupById(anyInt());
		verify(dao, times(1)).getGroupById(anyInt());
	}

}
