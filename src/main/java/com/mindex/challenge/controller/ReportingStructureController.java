package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@RestController
public class ReportingStructureController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

	@Autowired
	ReportingStructureService reportingStructureService;

   /**
    * This endpoint should accept an employeeId with
    * @param id
    * @return ReportingStructure, the fully filled out ReportingStructure for the specified employeeId
    * and the number of direct reports are computed on the fly and not getting persisted.
    */
	
	
	@GetMapping("/reportingstructure/{id}")
	public ReportingStructure readStructure(@PathVariable String id) {
		LOG.debug("Received request for reading reports structure for an employee by employeeId [{}]", id);

		return reportingStructureService.readStructure(id);

	}
}
