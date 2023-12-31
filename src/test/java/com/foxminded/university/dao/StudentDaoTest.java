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

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private StudentDao studentDao;

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
	void testInsertStudent() {
		Student expected = new Student();
		expected.setFirstName("123");
		expected.setLastName("321");
		Group group = new Group();
		group.setId(2);
		expected.setGroup(group);
		studentDao.save(expected);
		Student actual = studentDao.getReferenceById(studentDao.findAll().size());
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteById() {
		studentDao.deleteById(studentDao.findAll().get(1).getStudentId());
		int expected = 3;
		int actual = studentDao.findAll().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllStudents() {
		int expected = 4;
		int actual = studentDao.findAll().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetById() {
		Student expected = studentDao.findAll().get(1);
		Student actual = studentDao.getReferenceById(studentDao.findAll().get(1).getStudentId());
		assertEquals(expected, actual);
	}
	
	@Test
	void testUpdate() {
		Student expected = studentDao.findAll().get(1);
		expected.setFirstName("5555555");
		studentDao.save(expected);
		Student actual = studentDao.getReferenceById(2);
		assertEquals(expected, actual);
	}

}
