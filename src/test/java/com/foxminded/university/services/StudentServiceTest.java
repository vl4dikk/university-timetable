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

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	StudentDao dao;

	@InjectMocks
	StudentService service;

	@Test
	void testInsertStringString() {
		Student student = new Student();
		student.setFirstName(null);
		student.setLastName(null);
		service.insert(student);
		verify(dao, times(1)).insert(any(Student.class));
	}

	@Test
	void testInsertHashMapOfStringString() {
		HashMap<String, String> mapMock = new HashMap<>();
		mapMock.put("123", "321");
		mapMock.put("321", "123");
		service.insert(mapMock);
		verify(dao, times(2)).insert(any(Student.class));
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllStudents() {
		service.getAllStudents();
		verify(dao, times(1)).getAllStudents();
	}

	@Test
	void testGetStudentById() {
		service.getStudentById(anyInt());
		verify(dao, times(1)).getById(anyInt());
	}

	@Test
	void testUpdate() {
		Student student = new Student();
		service.update(student);;
		verify(dao, times(1)).update(any(Student.class));;
	}

}
