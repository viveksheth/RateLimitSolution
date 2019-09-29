package com.ratelimiting.controller;

import com.ratelimiting.model.Employee;
import com.ratelimiting.objects.ClientId;
import com.ratelimiting.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RateLimitControllerTest {

    @Mock
    private EmployeeServiceImpl employeeServiceImpl;

    @Test
    public void checkHealth() {
        RateLimitController rateLimitController = new RateLimitController();
        ResponseEntity<?> responseEntity = rateLimitController.checkHealth();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void generateAPIKey() {
        RateLimitController rateLimitController = new RateLimitController();
        ResponseEntity<?> responseEntity = rateLimitController.generateAPIKey();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}