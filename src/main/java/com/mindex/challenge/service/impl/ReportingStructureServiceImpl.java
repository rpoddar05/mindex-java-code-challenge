package com.mindex.challenge.service.impl;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	@Autowired
	EmployeeService employeeService;

	@Override
	public ReportingStructure readStructure(String id) {

		LOG.debug("reading reports structure of the employee by Id", id);

		int numberOfReporting = -1; // excluding first one itself from counting

		Queue<Employee> queue = new LinkedList<Employee>();
		ReportingStructure reportingStructure = new ReportingStructure();

		try {
			Employee employee = employeeService.read(id);
			queue.add(employee);

			while (!queue.isEmpty()) {

				Employee emp = queue.poll();
				numberOfReporting++;

				if (emp.getDirectReports() != null) {
					for (int i = 0; i < emp.getDirectReports().size(); i++) {

						Employee reportingEmployee = emp.getDirectReports().get(i);

						Employee empDetails = employeeService.read(reportingEmployee.getEmployeeId());

						reportingEmployee.setFirstName(empDetails.getFirstName());
						reportingEmployee.setLastName(empDetails.getLastName());
						reportingEmployee.setPosition(empDetails.getPosition());
						reportingEmployee.setDepartment(empDetails.getDepartment());
						reportingEmployee.setDirectReports(empDetails.getDirectReports());

						queue.add(reportingEmployee);
					}
				}
			}

			reportingStructure.setEmployee(employee);
			reportingStructure.setNumberOfReports(numberOfReporting);
		} catch (Exception e) {
			LOG.error("Execption occured : {} ", e);
		}

		return reportingStructure;

	}
}
