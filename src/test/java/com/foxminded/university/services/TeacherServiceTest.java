package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

	@Mock
	TeacherDao dao;

	@InjectMocks
	TeacherService service;

	@Test
	void testInsertStringString() {
		service.insert("123", "321");
		verify(dao, times(1)).save(any(Teacher.class));
	}

	@Test
	void testInsertHashMapOfStringString() {
		HashMap<String, String> mapMock = new HashMap<>();
		mapMock.put("321", "123");
		mapMock.put("123", "321");
		service.insert(mapMock);
		verify(dao, times(2)).save(any(Teacher.class));
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllTeachers() {
		service.getAllTeachers();
		verify(dao, times(1)).findAll();
	}

	@Test
	void testGetTeacherById() {
		service.getTeacherById(anyInt());
		verify(dao, times(1)).getReferenceById(anyInt());
	}

	@Test
	void testUpdate() {
		Teacher teacher = new Teacher();
		service.update(teacher);
		verify(dao, times(1)).save(any(Teacher.class));
	}

}
