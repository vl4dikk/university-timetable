package com.foxminded.university.controllers.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import com.foxminded.university.services.StudentService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;
	
	@Test
	@WithMockUser
	void testGetAllStudents() throws Exception {
		Student student1 = new Student();
		Student student2 = new Student();
		student1.setStudentId(1);
		student1.setFirstName("123");
		student1.setLastName("321");
		student2.setStudentId(2);
		student2.setFirstName("321");
		student2.setLastName("123");
		Group group = new Group();
		group.setId(1);
		student1.setGroup(group);
		student2.setGroup(group);
		List<Student> students = new LinkedList<>();
		students.add(student1);
		students.add(student2);
		
		Mockito.when(studentService.getAllStudents()).thenReturn(students);
		
		mockMvc.perform(get("/api/students")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
		.andExpect(jsonPath("$._embedded.studentList").isArray())
		.andExpect(jsonPath("$._embedded.studentList[*].studentId").exists())
		.andExpect(jsonPath("$._embedded.studentList[*].firstName").exists())
		.andExpect(jsonPath("$._embedded.studentList[*].lastName").exists())
		.andExpect(jsonPath("$._embedded.studentList[*].group.id").exists())
		.andExpect(jsonPath("$._embedded.studentList[*]._links.self.href").exists());
	}

	@Test
	@WithMockUser
	void testAddStudent() throws JsonProcessingException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Student student = new Student();
		student.setFirstName("123");
		student.setLastName("321");
		Group group = new Group();
		group.setId(1);
		student.setGroup(group);
		
        mockMvc.perform(post("/api/students")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
        
        Mockito.verify(studentService, Mockito.times(1)).insert(student);
		
	}

	@Test
	@WithMockUser
	void testGetStudentById() throws Exception {
		Student student = new Student();
		student.setStudentId(1);
		student.setFirstName("123");
		student.setLastName("321");
		Group group = new Group();
		group.setId(1);
		student.setGroup(group);
		
		Mockito.when(studentService.getStudentById(1)).thenReturn(student);

		mockMvc.perform(get("/api/students/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$.studentId").value(1))
				.andExpect(jsonPath("$.firstName").value("123"))
				.andExpect(jsonPath("$.lastName").value("321"))
				.andExpect(jsonPath("$.group.id").value(1))
				.andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	@WithMockUser
	void testUpdateStudent() throws JsonProcessingException, Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		Student student = new Student();
		student.setStudentId(1);
		student.setFirstName("123");
		student.setLastName("321");
		Group group = new Group();
		group.setId(1);
		student.setGroup(group);

		mockMvc.perform(put("/api/students/1").with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student))).andExpect(status().isOk());

		Mockito.verify(studentService, Mockito.times(1)).update(student);
		
	}

	@Test
	@WithMockUser
	void testDeleteStudent() throws Exception {
		mockMvc.perform(delete("/api/students/1").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithAnonymousUser
	void cannotGetStudentIfNotAuthorized() throws Exception {
		mockMvc.perform(get("/api/students/{id}", 1)).andExpect(status().isUnauthorized());
	}

}
