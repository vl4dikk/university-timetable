package com.foxminded.university.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.foxminded.university.configs.ThymeleafConfig;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lesson;
import com.foxminded.university.models.Audience;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.AudienceService;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.LessonService;
import com.foxminded.university.services.TeacherService;

@WebMvcTest(HomePageController.class)
@ContextConfiguration(classes = ThymeleafConfig.class)
@ExtendWith(MockitoExtension.class)
class LessonControllerTest {

	@Mock
	private LessonService service;
	@Mock
	private TeacherService teacherService;
	@Mock
	private GroupService groupService;
	@Mock
	private AudienceService audienceService;

	@InjectMocks
	private LessonController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllLessons() throws Exception {
		List<Lesson> expected = new LinkedList<>();
		mockMvc.perform(get("/lessons/getAllLessons")).andExpect(model().attribute("lessons", expected))
				.andExpect(view().name("lessons/getAllLessons"));
	}

	@Test
	public void testDeleteLesson() throws Exception {
		doNothing().when(service).deleteById(anyInt());

		mockMvc.perform(get("/lessons/deleteLesson/{id}", 1)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/lessons/getAllLessons"));

		verify(service, times(1)).deleteById(1);
	}

	@Test
	void testAddNewLessonForm() throws Exception {
		Lesson lesson = new Lesson();
		List<Teacher> teachers = new LinkedList<>();
		List<Group> groups = new LinkedList<>();
		List<Audience> audiences = new LinkedList<>();

		Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);
		Mockito.when(groupService.getAllGroups()).thenReturn(groups);
		Mockito.when(audienceService.getAllAudiences()).thenReturn(audiences);

		mockMvc.perform(get("/lessons/addNewLessonForm")).andExpect(model().attribute("lesson", lesson))
				.andExpect(model().attributeExists("lesson", "teachers", "groups", "audiences"))
				.andExpect(view().name("lessons/addNewLessonForm"));

		verify(teacherService, times(1)).getAllTeachers();
		verify(groupService, times(1)).getAllGroups();
		verify(audienceService, times(1)).getAllAudiences();
	}

	@Test
	void testInsertLesson() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setName("123");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		lesson.setTeacher(teacher);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setAudience(audience);
		LocalDateTime time = LocalDateTime.of(2023, 4, 15, 10, 0);
		lesson.setTime(time);

		Mockito.doNothing().when(service).insert(anyString(), anyInt(), anyInt(), anyInt(), any(LocalDateTime.class));

		mockMvc.perform(post("/lessons/insertLesson").flashAttr("lesson", lesson))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/lessons/getAllLessons"));

		Mockito.verify(service).insert(lesson.getName(), lesson.getTeacher().getTeacherId(), lesson.getGroup().getId(),
				lesson.getAudience().getAudienceId(), lesson.getTime());
	}

	@Test
	void testShowFormForUpdate() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setName("123");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		lesson.setTeacher(teacher);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setAudience(audience);
		LocalDateTime time = LocalDateTime.of(2023, 4, 15, 10, 0);
		lesson.setTime(time);
		List<Teacher> teachers = new LinkedList<>();
		List<Group> groups = new LinkedList<>();
		List<Audience> audiences = new LinkedList<>();

		Mockito.when(service.getById(anyInt())).thenReturn(lesson);
		Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);
		Mockito.when(groupService.getAllGroups()).thenReturn(groups);
		Mockito.when(audienceService.getAllAudiences()).thenReturn(audiences);

		mockMvc.perform(get("/lessons/showFormForUpdate/{id}", 1)).andExpect(status().isOk())
				.andExpect(view().name("lessons/updateLessonForm"))
				.andExpect(model().attributeExists("lesson", "teachers", "groups", "audiences"))
				.andExpect(model().attribute("lesson", Matchers.sameInstance(lesson)));

		verify(teacherService, times(1)).getAllTeachers();
		verify(groupService, times(1)).getAllGroups();
		verify(audienceService, times(1)).getAllAudiences();
	}

	@Test
	void testUpdateLesson() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setName("123");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		lesson.setTeacher(teacher);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setAudience(audience);
		LocalDateTime time = LocalDateTime.of(2023, 4, 15, 10, 0);
		lesson.setTime(time);

		Mockito.doNothing().when(service).update(Mockito.any(Lesson.class));

		mockMvc.perform(post("/lessons/updateLesson").flashAttr("lesson", lesson))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/lessons/getAllLessons"));

		Mockito.verify(service).update(lesson);
	}

	@Test
	void testUpdateLessonWithBlankName() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setName("");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		lesson.setTeacher(teacher);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setAudience(audience);
		LocalDateTime time = LocalDateTime.of(2023, 4, 15, 10, 0);
		lesson.setTime(time);

		mockMvc.perform(post("/lessons/updateLesson").flashAttr("lesson", lesson)).andExpect(status().isBadRequest());
	}

	@Test
	void testInsertLessonWithNullTime() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setName("123");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		lesson.setTeacher(teacher);
		Group group = new Group();
		group.setId(1);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(1);
		lesson.setAudience(audience);
		lesson.setTime(null);

		mockMvc.perform(post("/lessons/insertLesson").flashAttr("lesson", lesson)).andExpect(status().isBadRequest());
	}

}
