package com.foxminded.university.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
import com.foxminded.university.models.Audience;
import com.foxminded.university.services.AudienceService;

@WebMvcTest(AudienceController.class)
@ContextConfiguration(classes = ThymeleafConfig.class)
@ExtendWith(MockitoExtension.class)
class AudienceControllerTest {

	@Mock
	private AudienceService service;
	@InjectMocks
	private AudienceController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllAudiences() throws Exception {
		List<Audience> expected = new LinkedList<>();
		mockMvc.perform(get("/audiences/getAllAudiences")).andExpect(model().attribute("audiences", expected))
				.andExpect(view().name("audiences/getAllAudiences"));
	}

}
