package com.foxminded.university.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.foxminded.university.configs.ThymeleafConfig;
import com.foxminded.university.models.Student;
import com.foxminded.university.services.StudentService;

@WebMvcTest(HomePageController.class)
@ContextConfiguration(classes = ThymeleafConfig.class)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

	@Mock
	private StudentService service;
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

}
