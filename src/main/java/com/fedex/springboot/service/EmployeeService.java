package com.fedex.springboot.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fedex.springboot.model.Project;
import com.fedex.springboot.model.Employee;

@Component
public class EmployeeService {

	private static List<Employee> employees = new ArrayList<>();

	static {
		//Initialize Data
		Project eTNMS = new Project("eTNMS", "Enterprise Tracking Number Management System", "issues tracking number ranges");
		Project chan = new Project("CHAN", "Channel Master", "Authoritative Channels Data Source");
		Project ctns = new Project("CTNS", "Corporate Tracking Number System", "Issues tracking numbers for online systems");
		Project caas = new Project("CAAS", "Cust Auto *****","FedEX Customer Accounts");
		Project cicd = new Project("CI/CD", "Continuous Integration/Continuous Delivery","Making FAST APPS CI/CD enabled");

		Employee naveen = new Employee("naveenv", "Naveen",
				"Sr Programmer Analyst", new ArrayList<>(Arrays
						.asList(eTNMS, chan, cicd)));
		
		Employee robin = new Employee("robin", "Robin",
				"Project/Process Advisor", new ArrayList<>(Arrays
						.asList(eTNMS, chan, ctns)));

		Employee richard = new Employee("richard", "Richard",
				"Bus Appl Advisor", new ArrayList<>(Arrays
						.asList(chan, caas, ctns)));

		employees.add(naveen);
		employees.add(robin);
		employees.add(richard);
	}

	public List<Employee> retrieveAllEmployees() {
		return employees;
	}

	public Employee retrieveEmployee(String employeeId) {
		for (Employee employee : employees) {
			if (employee.getId().equals(employeeId)) {
				return employee;
			}
		}
		return null;
	}

	public List<String> retrieveProjects(String employeeId) {
		Employee employee = retrieveEmployee(employeeId);

		if (employee == null) {
			return null;
		}

		List<Project> projects = employee.getProjects();
		List<String> projIds = new ArrayList<String>();
		for(Project p : projects)
		{
			projIds.add(p.getId());
		}
		return projIds;
	}

	public Project retrieveProject(String employeeId, String projectId) {
		Employee employee = retrieveEmployee(employeeId);

		if (employee == null) {
			return null;
		}

		for (Project project : employee.getProjects()) {
			if (project.getId().equals(projectId)) {
				return project;
			}
		}

		return null;
	}

	private SecureRandom random = new SecureRandom();

	public Project addProject(String employeeId, Project project) {
		Employee employee = retrieveEmployee(employeeId);

		if (employee == null) {
			return null;
		}

		String randomId = new BigInteger(130, random).toString(32);
		project.setId(randomId);

		employee.getProjects().add(project);

		return project;
	}
}