package com.foxminded.university.services;

import java.io.InputStream;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.foxminded.university.configs.DaoConfig;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;

public class GroupService {
	
	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoConfig.class);

	private GroupDao dao = context.getBean("groupDao", GroupDao.class);
	
	public void test () {
//		List<Group> groups = dao.getAllGroups();
//		for(Group group : groups) {
//			System.out.println(group.toString());
//		}
		Group group = dao.getGroupById(2);
		System.out.println(group.toString());
	}

}
