package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

	@Mock
	TeacherDao dao;

	@InjectMocks
	TeacherService service;

	@Test
	void testInsertStringString() {
		service.insert("123", "321");
		verify(dao, times(1)).insert(any(Teacher.class));
	}

	@Test
	void testInsertHashMapOfStringString() {
		HashMap<String, String> mapMock = new HashMap<>();
		service.insert(mapMock);
		verify(dao, times(1)).insert(anyList());
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllTeachers() {
		service.getAllTeachers();
		verify(dao, times(1)).getAllTeachers();
	}

	@Test
	void testGetTeacherById() {
		service.getTeacherById(anyInt());
		verify(dao, times(1)).getTeacherById(anyInt());
	}

}
