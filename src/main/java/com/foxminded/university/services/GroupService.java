package com.foxminded.university.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;

@Service
public class GroupService {

	private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

	private GroupDao dao;

	@Autowired
	public GroupService(GroupDao dao) {
		this.dao = dao;
	}

	public void insert(String name) {
		logger.info("Started GroupService to insert group");
		Group group = new Group();
		group.setName(name);
		dao.save(group);
		logger.info("Group inserted");
	}

	public void insert(List<String> names) {
		logger.info("Started GroupService to insert list of groups");
		for (String name : names) {
			Group group = new Group();
			group.setName(name);
			dao.save(group);
			logger.info("Group inserted");
		}
	}

	public void deleteById(int groupId) {
		logger.info("Started GroupService to delete group by id");
		dao.deleteById(groupId);
	}

	public List<Group> getAllGroups() {
		logger.info("Started GroupService to get all groups");
		return dao.findAll();
	}

	public Group getGroupById(int groupId) {
		logger.info("Started GroupService to get group by id");
		return dao.getReferenceById(groupId);
	}

	public void update(Group group) {
		logger.info("Started GroupService to update group");
		dao.save(group);
	}
}
