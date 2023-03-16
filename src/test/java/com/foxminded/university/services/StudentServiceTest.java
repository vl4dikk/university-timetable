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

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Student;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	StudentDao dao;

	@InjectMocks
	StudentService service;

	@Test
	void testInsertStringString() {
		service.insert("123", "321");
		verify(dao, times(1)).insert(any(Student.class));
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
	void testAssignStudentToGroup() {
		service.assignStudentToGroup(1, 1);
		verify(dao, times(1)).assignStudentToGroup(1, 1);
	}

}
