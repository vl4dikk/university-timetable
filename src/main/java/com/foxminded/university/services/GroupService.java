package com.foxminded.university.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.exceptions.DAOException;
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
		logger.trace("Started GroupService to insert group");
		Group group = new Group();
		group.setName(name);
		dao.insert(group);
		logger.trace("Group with name {} inserted", name);
	}

	public void insert(List<String> names) {
		logger.trace("Started GroupService to insert list of groups");
		for (String name : names) {
			Group group = new Group();
			group.setName(name);
			dao.insert(group);
			logger.trace("Group with name {} inserted", name);
		}
	}

	public void deleteById(int groupId) {
		logger.trace("Started GroupService to delete group by id {}", groupId);
		dao.deleteById(groupId);
	}

	public List<Group> getAllGroups() {
		logger.trace("Started GroupService to get all groups");
		return dao.getAllGroups();
	}

	public Group getGroupById(int groupId) {
		logger.trace("Started GroupService to get group by id {}", groupId);
		return dao.getGroupById(groupId);
	}
}
