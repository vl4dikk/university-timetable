package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.foxminded.university.models.Group;


@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private GroupDao groupDao;
	

	@Test
	void testInsertGroup() {
		Group group = new Group();
		group.setId(1);
		group.setName("DaoTest");
		groupDao.insert(group);
		Group actual = groupDao.getGroupById(1);
		assertEquals(group, actual);
	}

	@Test
	void testInsertListOfGroup() {
		Group group = new Group();
		group.setId(1);
		group.setName("DaoTest");
		groupDao.insert(group);
		Group actual = groupDao.getGroupById(1);
		assertEquals(group, actual);
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllGroups() {
		fail("Not yet implemented");
	}

	@Test
	void testGetGroupById() {
		fail("Not yet implemented");
	}

}
