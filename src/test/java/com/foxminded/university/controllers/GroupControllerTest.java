package com.foxminded.university.controllers;

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
import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;

@WebMvcTest(GroupController.class)
@ContextConfiguration(classes = ThymeleafConfig.class)
@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

	@Mock
	private GroupService service;
	@InjectMocks
	private GroupController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllGroups() throws Exception {
		List<Group> expected = new LinkedList<>();
		mockMvc.perform(get("/groups/getAllGroups")).andExpect(model().attribute("groups", expected))
				.andExpect(view().name("groups/getAllGroups"));
	}

	@Test
	public void testDeleteGroup() throws Exception {
		doNothing().when(service).deleteById(anyInt());

		mockMvc.perform(get("/groups/deleteGroup/{id}", 1)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/groups/getAllGroups"));

		verify(service, times(1)).deleteById(1);
	}

	@Test
	void testAddNewGroupForm() throws Exception {
		Group group = new Group();
		mockMvc.perform(get("/groups/addNewGroupForm")).andExpect(model().attribute("group", group))
				.andExpect(view().name("groups/addNewGroupForm"));
	}

	@Test
	void testInsertGroup() throws Exception {
		Group group = new Group();
		group.setName("123");

		Mockito.doNothing().when(service).insert(Mockito.anyString());

		mockMvc.perform(post("/groups/insertGroup").flashAttr("group", group)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/groups/getAllGroups"));

		Mockito.verify(service).insert("123");
	}

	@Test
	void testShowFormForUpdate() throws Exception {
		Group group = new Group();
		group.setId(1);
		group.setName("123");

		Mockito.when(service.getGroupById(Mockito.anyInt())).thenReturn(group);

		mockMvc.perform(get("/groups/showFormForUpdate/{id}", 1)).andExpect(status().isOk())
				.andExpect(view().name("groups/updateGroupForm"))
				.andExpect(model().attribute("group", Matchers.sameInstance(group)));

		Mockito.verify(service).getGroupById(1);
	}

	@Test
	void testUpdateAudience() throws Exception {
		Group group = new Group();
		group.setName("123");

		Mockito.doNothing().when(service).update(Mockito.any(Group.class));

		mockMvc.perform(post("/groups/updateGroup").flashAttr("group", group)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/groups/getAllGroups"));

		Mockito.verify(service).update(group);
	}

}
