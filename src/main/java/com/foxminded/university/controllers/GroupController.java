package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupService service;

	@GetMapping("/getAllGroups")
	public String getAllGroups(Model model) {
		List<Group> groups = service.getAllGroups();
		model.addAttribute("groups", groups);
		return "groups/getAllGroups";
	}

}
