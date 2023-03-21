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
		logger.debug("Started GroupService to insert group");
		Group group = new Group();
		group.setName(name);
		try {
			dao.insert(group);
			logger.debug("Group with name {} inserted", name);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}

	public void insert(List<String> names) {
		logger.debug("Started GroupService to insert list of groups");
		for (String name : names) {
			Group group = new Group();
			group.setName(name);
			try {
				dao.insert(group);
				logger.debug("Group with name {} inserted", name);
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void deleteById(int groupId) {
		logger.debug("Started GroupService to delete group by id {}", groupId);
		dao.deleteById(groupId);
	}

	public List<Group> getAllGroups() {
        logger.debug("Started GroupService to get all groups");
		return dao.getAllGroups();
	}

	public Group getGroupById(int groupId) {
		logger.debug("Started GroupService to get group by id {}", groupId);
		Group group = new Group();
		try {
			group = dao.getGroupById(groupId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return group;
	}
}
