package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lesson;
import com.foxminded.university.models.Teacher;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LessonDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private LessonDao lessonDao;

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
	void testInsertLesson() {
		Lesson expected = new Lesson();
		Group group = new Group();
		Teacher teacher = new Teacher();
		Audience audience = new Audience();
		String str = "2016-03-04 11:30"; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime time = LocalDateTime.parse(str, formatter);
		group.setId(4);
		group.setName("DaoTest4");
		teacher.setTeacherId(4);
		teacher.setFirstName("Vlad4");
		teacher.setLastName("Valchuk4");
		audience.setAudienceId(4);
		audience.setAudienceNumber(34);
		expected.setGroup(group);
		expected.setAudience(audience);
		expected.setTeacher(teacher);
		expected.setTime(time);
		expected.setName("testLesson123");
		lessonDao.insert(expected);
		Lesson actual = lessonDao.getById(lessonDao.getAllLessons().size());
		assertEquals(expected, actual);
	}

	@Test
	void testDeleteById() {
		lessonDao.deleteById(lessonDao.getAllLessons().get(1).getLessonId());
		int expected = 3;
		int actual = lessonDao.getAllLessons().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllLessons() {
		int expected = 4;
		int actual = lessonDao.getAllLessons().size();
		assertEquals(expected, actual);
	}

	@Test
	void testGetById() {
		Lesson expected = lessonDao.getAllLessons().get(3);
		Lesson actual = lessonDao.getById(lessonDao.getAllLessons().get(3).getLessonId());
		assertEquals(expected, actual);
	}
	
	@Test
	void testUpdate() {
		Lesson expected = lessonDao.getAllLessons().get(3);
		expected.setName("333333333");
		lessonDao.update(expected);
		Lesson actual = lessonDao.getById(lessonDao.getAllLessons().get(3).getLessonId());
		assertEquals(expected, actual);
	}

}
