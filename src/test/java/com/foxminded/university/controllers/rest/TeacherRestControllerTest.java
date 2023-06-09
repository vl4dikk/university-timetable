package com.foxminded.university.controllers.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.TeacherService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TeacherRestController.class)
class TeacherRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TeacherService teacherService;

	@Test
	@WithMockUser
	void testGetAllTeachers() throws Exception {
		Teacher teacher1 = new Teacher();
		Teacher teacher2 = new Teacher();
		teacher1.setTeacherId(1);
		teacher2.setTeacherId(2);
		teacher1.setFirstName("123");
		teacher1.setLastName("321");
		teacher2.setFirstName("321");
		teacher2.setLastName("123");
		List<Teacher> teachers = new LinkedList<>();
		teachers.add(teacher1);
		teachers.add(teacher2);

		Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);

		mockMvc.perform(get("/api/teachers")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$._embedded.teacherList").isArray())
				.andExpect(jsonPath("$._embedded.teacherList[*].teacherId").exists())
				.andExpect(jsonPath("$._embedded.teacherList[*].firstName").exists())
				.andExpect(jsonPath("$._embedded.teacherList[*].lastName").exists())
				.andExpect(jsonPath("$._embedded.teacherList[*]._links.self.href").exists());
	}

	@Test
	@WithMockUser
	void testAddTeacher() throws JsonProcessingException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		Teacher teacher = new Teacher();
		teacher.setFirstName("123");
		teacher.setLastName("321");

		mockMvc.perform(post("/api/teachers").with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(teacher)))
				.andExpect(status().isOk());

		Mockito.verify(teacherService, Mockito.times(1)).insert(teacher.getFirstName(), teacher.getLastName());
	}

	@Test
	@WithMockUser
	void testGetTeacherById() throws Exception {
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		teacher.setFirstName("123");
		teacher.setLastName("321");

		Mockito.when(teacherService.getTeacherById(1)).thenReturn(teacher);

		mockMvc.perform(get("/api/teachers/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$.teacherId").value(1)).andExpect(jsonPath("$.firstName").value("123"))
				.andExpect(jsonPath("$.lastName").value("321")).andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	@WithMockUser
	void testUpdateTeacher() throws JsonProcessingException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		Teacher teacher = new Teacher();
		teacher.setTeacherId(1);
		teacher.setFirstName("123");
		teacher.setLastName("321");

		mockMvc.perform(put("/api/teachers/1").with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(teacher)))
				.andExpect(status().isOk());

		Mockito.verify(teacherService, Mockito.times(1)).update(teacher);
	}

	@Test
	@WithMockUser
	void testDeleteTeacher() throws Exception {
		mockMvc.perform(delete("/api/teachers/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@WithAnonymousUser
	void cannotGetTeacherIfNotAuthorized() throws Exception {
		mockMvc.perform(get("/api/teachers/{id}", 1)).andExpect(status().isUnauthorized());
	}

}
