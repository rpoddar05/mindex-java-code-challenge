package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

	private String compensationUrl;
	private String compensationIdUrl;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		compensationUrl = "http://localhost:" + port + "/compensation";
		compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
	}

	@Test
	public void createReadTest() {

		Employee testEmp = new Employee();

		testEmp.setFirstName("John");
		testEmp.setLastName("Doe");
		testEmp.setDepartment("Engineering");
		testEmp.setPosition("Developer");

		Compensation testCompensation = new Compensation();
		testCompensation.setEmployee(testEmp);
		testCompensation.setSalary(100000);
		testCompensation.setEffectiveDate("12/04/2020");

		// testing request for creating compensation which includes employee, salary and
		// effective date
		Compensation createdCompensation = restTemplate
				.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

		assertNotNull(createdCompensation.getEmployee().getEmployeeId());
		assertCompensationEquivalence(testCompensation, createdCompensation);

		
		// testing reading request for compensation by employee Id

		Compensation readCompensation = restTemplate
				.getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId())
				.getBody();
		assertCompensationEquivalence(createdCompensation, readCompensation);

	}

	private static void assertCompensationEquivalence(Compensation expectedResult, Compensation actualResult) {

		assertEquals(expectedResult.getSalary(), actualResult.getSalary());
		assertEquals(expectedResult.getEffectiveDate(), actualResult.getEffectiveDate());
		assertEquals(expectedResult.getEmployee().getFirstName(), actualResult.getEmployee().getFirstName());
		assertEquals(expectedResult.getEmployee().getLastName(), actualResult.getEmployee().getLastName());
		assertEquals(expectedResult.getEmployee().getPosition(), actualResult.getEmployee().getPosition());
		assertEquals(expectedResult.getEmployee().getDepartment(), actualResult.getEmployee().getDepartment());
		assertEquals(expectedResult.getEmployee().getDirectReports(), actualResult.getEmployee().getDirectReports());

	}
}
