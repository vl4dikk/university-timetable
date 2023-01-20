package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.testcontainers.containers.PostgreSQLContainer;

@ExtendWith(SpringExtension.class)

class GroupDaoTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private GroupDao groupDao;
	
	private static PostgreSQLContainer postgres = new PostgreSQLContainer();


	
	@BeforeAll
	public static void startDb() {
		postgres.start();
	}

	@AfterAll
	public static void stopDb() {
		postgres.stop();
	}

	@Test
	public void testMyDao() {
		DataSource dataSource = DataSourceBuilder.create().url(postgres.getJdbcUrl()).username(postgres.getUsername())
				.password(postgres.getPassword()).build();
	}

	@Test
	void testGroupDao() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertGroup() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertListOfGroup() {
		fail("Not yet implemented");
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
