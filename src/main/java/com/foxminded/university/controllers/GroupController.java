package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;

import jakarta.validation.Valid;

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
	
	@GetMapping("/deleteGroup/{id}")
	public String deleteGroup(@PathVariable (value = "id") int id) {
		service.deleteById(id);
		return "redirect:/groups/getAllGroups";
	}
	
	@GetMapping("/addNewGroupForm")
	public String addNewGroupForm(Model model) {
		Group group = new Group();
		model.addAttribute("group", group);
		return "groups/addNewGroupForm";
	}
	
	@PostMapping("/insertGroup")
	public String insertGroup(@ModelAttribute("group") @Valid Group group) {
		service.insert(group.getName());
		return "redirect:/groups/getAllGroups";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") int id, Model model) {	
		Group group = service.getGroupById(id);
		model.addAttribute("group", group);
		return "groups/updateGroupForm";
	}
	
	@PostMapping("/updateGroup")
	public String updateGroup(@ModelAttribute("group") @Valid Group group) {
		service.update(group);
		return "redirect:/groups/getAllGroups";
	}

}
