package com.fedex.springboot.model;

import java.util.List;

public class Employee {
	private String id;
	private String name;
	private String description;
	private List<Project> projects;

	public Employee(String id, String name, String description,
			List<Project> projects) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.projects = projects;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return String.format(
				"Employee [id=%s, name=%s, description=%s, projects=%s]", id,
				name, description, projects);
	}
}