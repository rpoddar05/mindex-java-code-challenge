package com.mindex.challenge.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

	@Autowired
	CompensationRepository compensationRepository;

	@Override
	public Compensation create(Compensation comp) {
		LOG.debug("creating compensation for an Employee", comp);
		
		if (comp.getEmployee().getEmployeeId() == null) {// considering new employee if id not passed
			comp.getEmployee().setEmployeeId(UUID.randomUUID().toString());
		}

		return compensationRepository.insert(comp);

	}

	@Override
	public Compensation read(String employeeId) {
		LOG.debug("reading compensation by Employee id", employeeId);
		Compensation comp =  compensationRepository.findCompensationByEmployeeEmployeeId(employeeId);
		
		if(comp == null) {
			throw new RuntimeException("Invalid employeeId: " + employeeId);
		}
		
		return comp;

	}

}
