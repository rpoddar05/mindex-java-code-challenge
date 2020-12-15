package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mindex.challenge.data.Compensation;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
	//it will use compensation.employee.employeeId
	Compensation findCompensationByEmployeeEmployeeId(String employeeId);
}