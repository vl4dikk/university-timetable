package com.foxminded.university.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.university.configs.DaoConfig;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;

public class GroupService {
	
	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoConfig.class);

	private GroupDao dao = context.getBean("groupDao", GroupDao.class);;
	
	public void test () {
		Group group = new Group();
		group.setName("jdbc4");
		dao.insert(group);
	}

}
