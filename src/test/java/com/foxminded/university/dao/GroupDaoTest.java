package com.foxminded.university.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.PostgreSQLContainer;

class GroupDaoTest {

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
