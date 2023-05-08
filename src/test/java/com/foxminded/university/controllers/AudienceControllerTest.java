package com.foxminded.university.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
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

	@Test
	public void testDeleteAudience() throws Exception {
		doNothing().when(service).deleteById(anyInt());

		mockMvc.perform(get("/audiences/deleteAudience/{id}", 1)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/audiences/getAllAudiences"));

		verify(service, times(1)).deleteById(1);
	}

	@Test
	void testAddNewAudienceForm() throws Exception {
		Audience audience = new Audience();
		List<Integer> existingNumbers = new LinkedList<>();
		mockMvc.perform(get("/audiences/addNewAudienceForm")).andExpect(model().attribute("audience", audience))
				.andExpect(model().attribute("existingNumbers", existingNumbers))
				.andExpect(view().name("audiences/addNewAudienceForm"));
	}

	@Test
	void testInsertAudience() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceNumber(123456);

		Mockito.doNothing().when(service).insert(Mockito.anyInt());

		mockMvc.perform(post("/audiences/insertAudience").flashAttr("audience", audience))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/audiences/getAllAudiences"));

		Mockito.verify(service).insert(123456);
	}

	@Test
	void testShowFormForUpdate() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceId(1);
		audience.setAudienceNumber(123456);
		List<Audience> audiences = Arrays.asList(audience);

		Mockito.when(service.getAudienceById(Mockito.anyInt())).thenReturn(audience);
		Mockito.when(service.getAllAudiences()).thenReturn(audiences);

		mockMvc.perform(get("/audiences/showFormForUpdate/{id}", 1)).andExpect(status().isOk())
				.andExpect(view().name("audiences/updateAudienceForm"))
				.andExpect(model().attributeExists("audience", "existingNumbers"))
				.andExpect(model().attribute("audience", Matchers.sameInstance(audience)))
				.andExpect(model().attribute("existingNumbers", Matchers.contains(audience.getAudienceNumber())));

		Mockito.verify(service).getAudienceById(1);
		Mockito.verify(service).getAllAudiences();
	}

	@Test
	void testUpdateAudience() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceNumber(123456);

		Mockito.doNothing().when(service).update(Mockito.any(Audience.class));

		mockMvc.perform(post("/audiences/updateAudience").flashAttr("audience", audience))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/audiences/getAllAudiences"));

		Mockito.verify(service).update(audience);
	}

	@Test
	void testInsertAudienceWithAudienceNumberLessThanOne() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceNumber(0);

		mockMvc.perform(post("/audiences/insertAudience")).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateAudienceWithAudienceNumberLessThanOne() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceNumber(-123456);

		mockMvc.perform(post("/audiences/updateAudience").flashAttr("audience", audience))
				.andExpect(status().isBadRequest());
	}

}
