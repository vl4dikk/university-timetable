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

import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GroupRestController.class)
class GroupRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GroupService groupService;

	@Test
	@WithMockUser
	void testGetAllGroups() throws Exception {
		Group group1 = new Group();
		Group group2 = new Group();
		group1.setName("123");
		group1.setId(1);
		group2.setId(2);
		group2.setName("321");
		List<Group> groups = new LinkedList<>();
		groups.add(group1);
		groups.add(group2);

		Mockito.when(groupService.getAllGroups()).thenReturn(groups);

		mockMvc.perform(get("/api/groups")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$._embedded.groupList").isArray())
				.andExpect(jsonPath("$._embedded.groupList[*].id").exists())
				.andExpect(jsonPath("$._embedded.groupList[*].name").exists())
				.andExpect(jsonPath("$._embedded.groupList[*]._links.self.href").exists());
	}

	@Test
	@WithMockUser
	void testAddGroup() throws Exception {

		mockMvc.perform(post("/api/groups").with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON).content("{\"name\":123}"))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser
	void testGetGroupById() throws Exception {
		Group group = new Group();
		group.setId(1);
		group.setName("123");

		Mockito.when(groupService.getGroupById(1)).thenReturn(group);

		mockMvc.perform(get("/api/groups/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	@WithMockUser
	void testUpdateGroup() throws Exception {
		mockMvc.perform(put("/api/groups/1").with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"name\":123}"))
				.andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":'123'}"));
	}

	@Test
	@WithMockUser
	void testDeleteGroup() throws Exception {
		mockMvc.perform(delete("/api/groups/1").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithAnonymousUser
	void cannotGetGroupIfNotAuthorized() throws Exception {
		mockMvc.perform(get("/api/groups/{id}", 1)).andExpect(status().isUnauthorized());
	}

}
