package com.foxminded.university.controllers;

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
import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.TeacherService;

@WebMvcTest(HomePageController.class)
@ContextConfiguration(classes = ThymeleafConfig.class)
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

	@Mock
	private TeacherService service;
	@InjectMocks
	private TeacherController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllTeachers() throws Exception {
		List<Teacher> expected = new LinkedList<>();
		mockMvc.perform(get("/teachers/getAllTeachers")).andExpect(model().attribute("teachers", expected))
				.andExpect(view().name("teachers/getAllTeachers"));
	}

	@Test
	public void testDeleteTeacher() throws Exception {
		doNothing().when(service).deleteById(anyInt());

		mockMvc.perform(get("/teachers/deleteTeacher/{id}", 1)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/teachers/getAllTeachers"));

		verify(service, times(1)).deleteById(1);
	}

	@Test
	void testAddNewTeacherForm() throws Exception {
		Teacher teacher = new Teacher();

		mockMvc.perform(get("/teachers/addNewTeacherForm")).andExpect(model().attribute("teacher", teacher))
				.andExpect(view().name("teachers/addNewTeacherForm"));
	}

	@Test
	void testInsertTeacher() throws Exception {
		Teacher teacher = new Teacher();
		teacher.setFirstName("123");
		teacher.setLastName("123");

		Mockito.doNothing().when(service).insert(anyString(), anyString());

		mockMvc.perform(post("/teachers/insertTeacher").flashAttr("teacher", teacher))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/teachers/getAllTeachers"));

		Mockito.verify(service).insert("123", "123");
	}

	@Test
	void testShowFormForUpdate() throws Exception {
		Teacher teacher = new Teacher();

		Mockito.when(service.getTeacherById(anyInt())).thenReturn(teacher);

		mockMvc.perform(get("/teachers/showFormForUpdate/{id}", 1)).andExpect(status().isOk())
				.andExpect(view().name("teachers/updateTeacherForm"))
				.andExpect(model().attribute("teacher", Matchers.sameInstance(teacher)));

		Mockito.verify(service).getTeacherById(1);
	}

	@Test
	void testUpdateTeacher() throws Exception {
		Teacher teacher = new Teacher();

		Mockito.doNothing().when(service).update(Mockito.any(Teacher.class));

		mockMvc.perform(post("/teachers/updateTeacher").flashAttr("teacher", teacher))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/teachers/getAllTeachers"));

		Mockito.verify(service).update(teacher);
	}

}
