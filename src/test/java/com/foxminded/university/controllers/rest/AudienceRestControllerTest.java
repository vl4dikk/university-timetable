package com.foxminded.university.controllers.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.MockMvc;

import com.foxminded.university.models.Audience;
import com.foxminded.university.services.AudienceService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AudienceRestController.class)
class AudienceRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AudienceService audienceService;

	@Test
	public void testGetAllAudiences() throws Exception {
		Audience audience1 = new Audience();
		Audience audience2 = new Audience();
		audience1.setAudienceId(1);
		audience1.setAudienceNumber(123);
		audience2.setAudienceId(2);
		audience2.setAudienceNumber(321);
		List<Audience> audiences = new LinkedList<>();
		audiences.add(audience1);
		audiences.add(audience2);
		Mockito.when(audienceService.getAllAudiences()).thenReturn(audiences);

		mockMvc.perform(get("/api/audiences")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$._embedded.audienceList").isArray())
				.andExpect(jsonPath("$._embedded.audienceList[*].audienceId").exists())
				.andExpect(jsonPath("$._embedded.audienceList[*].audienceNumber").exists())
				.andExpect(jsonPath("$._embedded.audienceList[*]._links.self.href").exists());
	}

	@Test
	public void testAddAudience() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceNumber(123);

		mockMvc.perform(
				post("/api/audiences").contentType(MediaType.APPLICATION_JSON).content("{\"audienceNumber\":123}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetAudienceById() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceId(1);
		audience.setAudienceNumber(123);
		Mockito.when(audienceService.getAudienceById(1)).thenReturn(audience);

		mockMvc.perform(get("/api/audiences/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json")))
				.andExpect(jsonPath("$.audienceId").value(1)).andExpect(jsonPath("$.audienceNumber").exists())
				.andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	public void testUpdateAudience() throws Exception {
		Audience audience = new Audience();
		audience.setAudienceId(1);
		audience.setAudienceNumber(123);

		mockMvc.perform(put("/api/audiences/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"audienceId\":1,\"audienceNumber\":123}")).andExpect(status().isOk())
				.andExpect(content().json("{\"audienceId\":1,\"audienceNumber\":123}"));
	}

	@Test
	public void testDeleteAudience() throws Exception {
		mockMvc.perform(delete("/api/audiences/1")).andExpect(status().isNoContent());
	}

}
