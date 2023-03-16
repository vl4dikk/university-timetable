package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.foxminded.university.models.Audience;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AudienceDaoTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private AudienceDao audienceDao;

	private final static String SCRIPT_DB = "db.sql";

	@Container
	@ClassRule
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11-alpine")
			.withDatabaseName("integration-tests-db").withPassword("inmemory").withUsername("inmemory")
			.withInitScript(SCRIPT_DB);

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
	}

	@AfterAll
	public static void stopContainer() {
		postgreSQLContainer.stop();
	}

	@Test
	void testInsertAudience() {
		Audience audience = new Audience();
		audience.setAudienceNumber(15);
		audienceDao.insert(audience);
		int expected = 5;
		int actual = audienceDao.getAllAudiences().size();
		assertEquals(expected, actual);
	}

	@Test
	void testInsertListOfAudience() {
		Audience audience1 = new Audience();
		Audience audience2 = new Audience();
		audience1.setAudienceNumber(15);
		audience2.setAudienceId(16);
		List<Audience> audiences = new LinkedList<>();
		audiences.add(audience1);
		audiences.add(audience2);
		audienceDao.insert(audiences);
		int expected = 6;
		int actual = audienceDao.getAllAudiences().size();
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteById() {
		List<Audience> audiences = audienceDao.getAllAudiences();
		audienceDao.deleteById(audiences.get(1).getAudienceId());
		int expected = 3;
		int actual = audienceDao.getAllAudiences().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllAudiences() {
		int expected = 4;
		int actual = audienceDao.getAllAudiences().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAudienceById() {
		Audience expected = audienceDao.getAllAudiences().get(1);
		Audience actual = audienceDao.getAudienceById(audienceDao.getAllAudiences().get(1).getAudienceId());
		assertEquals(expected, actual);
	}

}
