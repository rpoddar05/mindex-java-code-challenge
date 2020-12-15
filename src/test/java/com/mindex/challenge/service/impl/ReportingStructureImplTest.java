package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String employeeUrl;
	private String readingstructureIdUrl;

	@Before
	public void setup() {
		employeeUrl = "http://localhost:" + port + "/employee";
		readingstructureIdUrl = "http://localhost:" + port + "/reportingstructure/{id}";
	}

	@Test
	public void readReportingStructureTest() {

		Employee testEmp1 = new Employee();
		testEmp1.setFirstName("John");
		testEmp1.setLastName("Doe");
		testEmp1.setDepartment("Engineering");
		testEmp1.setPosition("Manager");

		Employee testEmp2 = new Employee();
		testEmp2.setFirstName("Paul");
		testEmp2.setLastName("Doe");
		testEmp2.setDepartment("Engineering");
		testEmp2.setPosition("Senior Developer");

		Employee testEmp3 = new Employee();
		testEmp3.setFirstName("Ricky");
		testEmp3.setLastName("Cooney");
		testEmp3.setDepartment("Engineering");
		testEmp3.setPosition("Junior Developer");

		Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmp2, Employee.class).getBody();
		Employee createdEmployee3 = restTemplate.postForEntity(employeeUrl, testEmp3, Employee.class).getBody();

		assertNotNull(createdEmployee2.getEmployeeId());
		assertNotNull(createdEmployee3.getEmployeeId());

		testEmp1.setDirectReports(Arrays.asList(createdEmployee2, createdEmployee3));

		Employee createdEmployee1 = restTemplate.postForEntity(employeeUrl, testEmp1, Employee.class).getBody();

		assertNotNull(createdEmployee1.getEmployeeId());

		ReportingStructure testreportingStructure = new ReportingStructure();

		testreportingStructure.setEmployee(createdEmployee1);
		testreportingStructure.setNumberOfReports(2);

		ReportingStructure readreportingStructure = restTemplate
				.getForEntity(readingstructureIdUrl, ReportingStructure.class, createdEmployee1.getEmployeeId())
				.getBody();

		assertEquals(testreportingStructure.getNumberOfReports(), readreportingStructure.getNumberOfReports());

		assertEquals(testreportingStructure.getEmployee().getFirstName(),
				readreportingStructure.getEmployee().getFirstName());
		assertEquals(testreportingStructure.getEmployee().getLastName(),
				readreportingStructure.getEmployee().getLastName());

	}

}
