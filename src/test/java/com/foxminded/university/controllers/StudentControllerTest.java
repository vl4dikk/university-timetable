package com.foxminded.university.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.foxminded.university.models.Student;
import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.StudentService;

@WebMvcTest(HomePageController.class)
@ContextConfiguration(classes = ThymeleafConfig.class)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

	@Mock
	private StudentService service;
	@Mock
	private GroupService groupService;

	@InjectMocks
	private StudentController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllStudents() throws Exception {
		List<Student> expected = new LinkedList<>();
		mockMvc.perform(get("/students/getAllStudents")).andExpect(model().attribute("students", expected))
				.andExpect(view().name("students/getAllStudents"));
	}

	@Test
	public void testDeleteStudent() throws Exception {
		doNothing().when(service).deleteById(anyInt());

		mockMvc.perform(get("/students/deleteStudent/{id}", 1)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/students/getAllStudents"));

		verify(service, times(1)).deleteById(1);
	}

	@Test
	void testAddNewStudentForm() throws Exception {
		Student student = new Student();
		List<Group> groups = new LinkedList<>();
		Mockito.when(groupService.getAllGroups()).thenReturn(groups);
		mockMvc.perform(get("/students/addNewStudentForm")).andExpect(model().attribute("student", student))
				.andExpect(model().attribute("groups", groups)).andExpect(view().name("students/addNewStudentForm"));
		verify(groupService, times(1)).getAllGroups();
	}

	@Test
	void testInsertStudent() throws Exception {
		Student student = new Student();

		Mockito.doNothing().when(service).insert(any(Student.class));

		mockMvc.perform(post("/students/insertStudent").flashAttr("student", student))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/students/getAllStudents"));

		Mockito.verify(service).insert(student);
	}

	@Test
	void testShowFormForUpdate() throws Exception {
		Student student = new Student();
		List<Group> groups = new LinkedList<>();

		Mockito.when(service.getStudentById(anyInt())).thenReturn(student);
		Mockito.when(groupService.getAllGroups()).thenReturn(groups);

		mockMvc.perform(get("/students/showFormForUpdate/{id}", 1)).andExpect(status().isOk())
				.andExpect(view().name("students/updateStudentForm"))
				.andExpect(model().attributeExists("student", "groups"))
				.andExpect(model().attribute("student", Matchers.sameInstance(student)))
				.andExpect(model().attribute("groups", groups));

		Mockito.verify(groupService).getAllGroups();
	}

	@Test
	void testUpdateStudent() throws Exception {
		Student student = new Student();
		student.setFirstName("123");
		student.setLastName("321");
		Group group = new Group();
		group.setId(1);
		group.setName("123321");
		student.setGroup(group);

		Mockito.doNothing().when(service).update(Mockito.any(Student.class));

		mockMvc.perform(post("/students/updateStudent").flashAttr("student", student))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/students/getAllStudents"));

		Mockito.verify(service).update(student);
	}

}
