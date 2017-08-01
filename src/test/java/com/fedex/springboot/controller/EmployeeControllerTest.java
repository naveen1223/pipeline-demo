package com.fedex.springboot.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fedex.springboot.model.Project;
import com.fedex.springboot.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, secure = false)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	Project mockProject = new Project("Project1", "Jenkins", "CI CD");

	String exampleProjectJson = "{\"name\":\"Jenkins\",\"description\":\"CI CD\"}";

	@Test
	public void retrieveDetailsForProject() throws Exception {

		Mockito.when(
				employeeService.retrieveProject(Mockito.anyString(),
						Mockito.anyString())).thenReturn(mockProject);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/Employee1/projects/Project1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		//String expected = "{id:Project1,name:Jenkins,description:CI CD}";

		String expected = "{\"id\":\"Project1\",\"name\":\"Jenkins\",\"description\":\"CI CD\"}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	public void createEmployeeProject() throws Exception {
		Project mockProject = new Project("1", "Smallest Number", "1");

		// employeeService.addProject to respond back with mockProject
		Mockito.when(
				employeeService.addProject(Mockito.anyString(),
						Mockito.any(Project.class))).thenReturn(mockProject);

		// Send project as body to /employees/Employee1/projects
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/employees/Employee1/projects")
				.accept(MediaType.APPLICATION_JSON).content(exampleProjectJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/employees/Employee1/projects/1",
				response.getHeader(HttpHeaders.LOCATION));

	}

}
