package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

	@Autowired
	CompensationService compensationService;

	/**
	 * this endpoint is creating a Compensation for a new or existing employee along with the details, it's associated salary
	 * and effective date.
	 * It will also generate new UUID as employeeID in case if employeeId is not existing,considering 
	 * that employee as new in the DB. 
	 */
	
	@PostMapping("/compensation")
	public Compensation create(@RequestBody Compensation comp) {
		LOG.debug("received compensation create request for an employee [{}]", comp);
		return compensationService.create(comp);

	}
	
	/**
	 * This endpoint should receive request for reading compensation by employeeId
	 * @param id
	 * @return Compensation, which include employee details, salary and associated effective date.
	 *
	 */

	@GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
		LOG.debug("received request for reading compensation by employeeId [{}]", id);

		return compensationService.read(id);
	}
}
