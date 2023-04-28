package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

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

import com.foxminded.university.models.Teacher;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeacherDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private TeacherDao teacherDao;

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
	void testInsertTeacher() {
		Teacher expected = new Teacher();
		expected.setFirstName("123");
		expected.setLastName("321");
		teacherDao.save(expected);	
		Teacher actual = teacherDao.getReferenceById(teacherDao.findAll().size());
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteById() {
		teacherDao.deleteById(teacherDao.findAll().get(1).getTeacherId());
		int expected = 3;
		int actual = teacherDao.findAll().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllGroups() {
		int expected = 4;
		int actual = teacherDao.findAll().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetTeacherById() {
		Teacher expected = teacherDao.findAll().get(1);
		Teacher actual = teacherDao.getReferenceById(teacherDao.findAll().get(1).getTeacherId());
		assertEquals(expected, actual);
	}
	
	@Test
	void testUpdate() {
		Teacher expected = teacherDao.findAll().get(1);
		expected.setFirstName("777777");
		int teacherId = expected.getTeacherId();
		teacherDao.save(expected);
		Teacher actual = teacherDao.getReferenceById(teacherId);
		assertEquals(expected, actual);
	}

}
