package com.ratelimiting.service;

import com.ratelimiting.controller.RateLimitController;
import com.ratelimiting.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private List<Employee> employeeList = new ArrayList<Employee>();

    public EmployeeServiceImpl(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void loadEmployeeData()
    {
        logger.info("Loading employee data");


        Employee employee1 = new Employee(1,"John","Smith");
        Employee employee2 = new Employee(2,"Samantha","Ramshaw");
        Employee employee3 = new Employee(3,"Peter","Gates");
        Employee employee4 = new Employee(4,"Taylor","Kim");

        employeeList.add(employee1);

        employeeList.add(employee2);

        employeeList.add(employee3);

        employeeList.add(employee4);

        logger.info("loaded employee data successfully!");

    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public List<Employee> getAllEmployeeInfo() {
        return employeeList;
    }
}
