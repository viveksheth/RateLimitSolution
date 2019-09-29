package com.ratelimiting.controller;

import com.ratelimiting.model.Employee;
import com.ratelimiting.service.EmployeeServiceImpl;
import com.ratelimiting.service.RateLimitingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


@RestController
@EnableAutoConfiguration
public class RateLimitController {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitController.class);

    @Value("${calllimit}")
    int callLimitPerMinute;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private RateLimitingServiceImpl rateLimitingServiceImpl;

    public ServletContext getServletContext() {
        return servletContext;
    }

    @RequestMapping("/health")
    public ResponseEntity<?> checkHealth() {
        return new ResponseEntity("RateLimiter API health check is successful", HttpStatus.OK);
    }

    @RequestMapping("/generateClientId")
    ResponseEntity<?> generateAPIKey() {
        logger.info("Generating Client Id");
        try {
            InetAddress.getLocalHost();

            return new ResponseEntity("Successfully generated Client Id: "+ InetAddress.getLocalHost().getHostAddress().replace(".", ""), HttpStatus.OK);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return new ResponseEntity("FAILED to generate Client Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/getEmployeeData/{clientId}")
    ResponseEntity<?> getEmployeeData(@PathVariable("clientId") String clientId)
    {

        List<Employee> employees = employeeServiceImpl.getAllEmployeeInfo();

        if(employees.size()==0)
            employeeServiceImpl.loadEmployeeData();

        if(!rateLimitingServiceImpl.isRequestAllowed(clientId))
        {

                long nextTime = rateLimitingServiceImpl.nextAvailableTime();
                return new ResponseEntity<>("Too many requests. Please try again after " + nextTime+" seconds", HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
}
