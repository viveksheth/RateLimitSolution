package com.ratelimiting;

import com.ratelimiting.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RateLimitingClientTests {

    public static final String API_URI = "http://localhost:8080/getEmployeeData/12343252";

    private List<Employee> employeeList;

    public static void main(String[] arg)
    {
       TestRateLimit_More_than_100_requestLimit_PerHour(110);

    }

    public static void TestRateLimit_More_than_100_requestLimit_PerHour(int reqCount) {

        RestTemplate restTemplate = null;
        int count = 0;
        ResponseEntity<String> response = null;
        for(int i=1; i<=reqCount; i++)
        {
            restTemplate = new RestTemplate();
            try
            {
                response = restTemplate.getForEntity(API_URI,String.class);
            }
            catch (HttpClientErrorException exception)
            {
                    ++count;
            }
        }
        System.out.println("\n\nRejected Requests: " + count);
    }
}
