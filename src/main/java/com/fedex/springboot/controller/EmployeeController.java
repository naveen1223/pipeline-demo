package com.fedex.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fedex.springboot.model.Project;
import com.fedex.springboot.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees/{employeeId}/projects")
	public List<String> retrieveProjectsForEmployee(@PathVariable String employeeId) {
		return employeeService.retrieveProjects(employeeId);
	}
	
	@GetMapping("/employees/{employeeId}/projects/{projectId}")
	public Project retrieveDetailsForProject(@PathVariable String employeeId,
			@PathVariable String projectId) {
		return employeeService.retrieveProject(employeeId, projectId);
	}
	
	@PostMapping("/employees/{employeeId}/projects")
	public ResponseEntity<Void> registerEmployeeForProject(
			@PathVariable String employeeId, @RequestBody Project newProject) {

		Project project = employeeService.addProject(employeeId, newProject);

		if (project == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(project.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

}