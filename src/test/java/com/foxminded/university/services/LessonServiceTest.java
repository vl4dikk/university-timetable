package com.foxminded.university.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.university.dao.LessonDao;
import com.foxminded.university.models.Lesson;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

	@Mock
	LessonDao dao;

	@InjectMocks
	LessonService service;

	@Test
	void testInsertStringIntIntIntLocalDateTime() {
		service.insert("123", 1, 2, 3, LocalDateTime.now());
		verify(dao, times(1)).insert(any(Lesson.class));
	}

	@Test
	void testDeleteById() {
		service.deleteById(anyInt());
		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void testGetAllLessons() {
		service.getAllLessons();
		verify(dao, times(1)).getAllLessons();
	}

	@Test
	void testGetById() {
		service.getById(anyInt());
		verify(dao, times(1)).getById(anyInt());
	}
	
	@Test
	void testUpdate() {
		Lesson lesson = new Lesson();
		service.update(lesson);;
		verify(dao, times(1)).update(any(Lesson.class));;
	}

}
