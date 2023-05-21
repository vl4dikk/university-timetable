package com.foxminded.university.controllers.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.foxminded.university.models.Audience;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lesson;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.LessonService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LessonRestController.class)
class LessonRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LessonService lessonService;

	@Test
	void testGetAllLessons() throws Exception {

		List<Lesson> lessons = new LinkedList<>();
		Lesson lesson1 = new Lesson();
		Lesson lesson2 = new Lesson();
		lesson1.setLessonId(1);
		lesson2.setLessonId(2);
		lesson1.setName("testLesson1");
		lesson2.setName("testLesson2");
		Teacher teacher1 = new Teacher();
		Teacher teacher2 = new Teacher();
		teacher1.setTeacherId(1);
		teacher2.setTeacherId(2);
		lesson1.setTeacher(teacher1);
		lesson2.setTeacher(teacher2);
		Group group1 = new Group();
		Group group2 = new Group();
		group1.setId(1);
		group2.setId(2);
		lesson1.setGroup(group1);
		lesson2.setGroup(group2);
		Audience audience1 = new Audience();
		Audience audience2 = new Audience();
		audience1.setAudienceId(1);
		audience2.setAudienceId(2);
		lesson1.setAudience(audience1);
		lesson2.setAudience(audience2);
		lesson1.setTime(LocalDateTime.now());
		lesson2.setTime(LocalDateTime.now());
		lessons.add(lesson1);
		lessons.add(lesson2);

		Mockito.when(lessonService.getAllLessons()).thenReturn(lessons);

		mockMvc.perform(get("/api/lessons")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$._embedded.lessonList", hasSize(2)))
				.andExpect(jsonPath("$._embedded.lessonList[0].lessonId", is(1)))
				.andExpect(jsonPath("$._embedded.lessonList[0].name", is("testLesson1")))
				.andExpect(jsonPath("$._embedded.lessonList[0].teacher.teacherId", is(1)))
				.andExpect(jsonPath("$._embedded.lessonList[0].group.id", is(1)))
				.andExpect(jsonPath("$._embedded.lessonList[0].audience.audienceId", is(1)))
				.andExpect(jsonPath("$._embedded.lessonList[*]._links.self.href").exists());
	}

	@Test
	void testAddLesson() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Lesson lesson = new Lesson();
		lesson.setLessonId(1);
		lesson.setName("testLesson");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setGroup(group);
		lesson.setTeacher(teacher);
		lesson.setAudience(audience);
		lesson.setTime(LocalDateTime.now());

		mockMvc.perform(post("/api/lessons").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(lesson))).andExpect(status().isOk());

		Mockito.verify(lessonService, Mockito.times(1)).insert(lesson.getName(), lesson.getTeacher().getTeacherId(),
				lesson.getGroup().getId(), lesson.getAudience().getAudienceId(), lesson.getTime());

	}

	@Test
	void testGetLessonById() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setLessonId(1);
		lesson.setName("testLesson");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setGroup(group);
		lesson.setTeacher(teacher);
		lesson.setAudience(audience);

		Mockito.when(lessonService.getById(1)).thenReturn(lesson);

		mockMvc.perform(get("/api/lessons/1")).andExpect(status().isOk()).andExpect(jsonPath("$.lessonId", is(1)))
				.andExpect(jsonPath("$.name", is("testLesson"))).andExpect(jsonPath("$.teacher.teacherId", is(1)))
				.andExpect(jsonPath("$.group.id", is(1))).andExpect(jsonPath("$.audience.audienceId", is(1)));
	}

	@Test
	void testUpdateLesson() throws JsonProcessingException, Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Lesson lesson = new Lesson();
		lesson.setLessonId(1);
		lesson.setName("testLesson");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setGroup(group);
		lesson.setTeacher(teacher);
		lesson.setAudience(audience);
		lesson.setTime(LocalDateTime.now());

		mockMvc.perform(put("/api/lessons/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(lesson))).andExpect(status().isOk());

		Mockito.verify(lessonService, Mockito.times(1)).update(lesson);
	}

	@Test
	void testDeleteLesson() throws Exception {
		mockMvc.perform(delete("/api/lessons/1")).andExpect(status().isNoContent());

		Mockito.verify(lessonService, Mockito.times(1)).deleteById(1);
	}

}
