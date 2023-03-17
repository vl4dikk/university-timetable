package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

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

import com.foxminded.university.models.Group;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GroupDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private GroupDao groupDao;

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
	void testInsertGroup() {
		Group expected = new Group();
		expected.setName("DaoTest321");
		expected.setId(groupDao.getAllGroups().size() + 1);
		groupDao.insert(expected);
		Group actual = groupDao.getGroupById(groupDao.getAllGroups().size());
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteById() {
		List<Group> groups = groupDao.getAllGroups();
		groupDao.deleteById(groups.get(1).getId());
		int expected = 3;
		int actual = groupDao.getAllGroups().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllGroups() {
		int expected = 4;
		int actual = groupDao.getAllGroups().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetGroupById() {
		Group expected = groupDao.getAllGroups().get(1);
		Group actual = groupDao.getGroupById(groupDao.getAllGroups().get(1).getId());
		assertEquals(expected, actual);
	}

}
