package com.foxminded.university.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;

@Service
public class GroupService {

	private GroupDao dao;

	@Autowired
	public GroupService(GroupDao dao) {
		this.dao = dao;
	}

	public void insert(String name) {
		Group group = new Group();
		group.setName(name);
		dao.insert(group);
	}

	public void insert(List<String> names) {
		List<Group> groups = new LinkedList<>();
		for (String name : names) {
			Group group = new Group();
			group.setName(name);
			groups.add(group);
		}
		dao.insert(groups);
	}

	public void deleteById(int groupId) {
		dao.deleteById(groupId);
	}

	public List<Group> getAllGroups() {
		return dao.getAllGroups();
	}

	public Group getGroupById(int groupId) {
		return dao.getGroupById(groupId);
	}
}
