package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock
	GroupDao dao;

	@InjectMocks
	GroupService service;

	@Test
	void testInsert() {
		service.insert(anyString());
		verify(dao, times(1)).save(any(Group.class));
	}

	@Test
	void testInsertListOfString() {
		List<String> test = new LinkedList<>();
		test.add("123");
		test.add("321");
		service.insert(test);
		verify(dao, times(2)).save(any(Group.class));
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllGroups() {
		service.getAllGroups();
		verify(dao, times(1)).findAll();
	}

	@Test
	void testGetGroupById() {
		service.getGroupById(anyInt());
		verify(dao, times(1)).getReferenceById(anyInt());
	}

	@Test
	void testUpdate() {
		Group group = new Group();
		service.update(group);
		verify(dao, times(1)).save(any(Group.class));
	}

}
