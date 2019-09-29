package com.ratelimiting.service;

import com.ratelimiting.controller.RateLimitController;
import com.ratelimiting.objects.ClientId;
import com.sun.media.jfxmedia.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service("rateLimitingService")
public class RateLimitingServiceImpl implements RateLimitingService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private  ClientId clientIdInfo = null;

    @Autowired
    private RateLimitController rateLimitController;

    private ServletContext servletContext;

    private long minutes, seconds;

    @Value("${calllimit}")
    int allowedRequestLimit;

    @Value("${rateLimitWindowInMinutes}")
    int rateLimitWindowInMinutes;

    /*
        Method to check rate limit
     */
    @Override
    public boolean isRequestAllowed(String clientId) {

        servletContext = rateLimitController.getServletContext();

        //check if client is already exists in servletcontext or not
        if(servletContext.getAttribute(clientId)!=null)
        {
            clientIdInfo = (ClientId) servletContext.getAttribute(clientId);

            //Increment request count for specific client Id
            clientIdInfo.getReqCount().getAndIncrement();

            //Calculate time difference using last request timestamp to current time
            Long timeDifference = System.currentTimeMillis() - clientIdInfo.getLastCallTimeStamp();
            minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
            seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference);

            logger.info("Last Call: " + minutes + " minutes ago and " + seconds + " seconds ago");

            if(minutes<=rateLimitWindowInMinutes && clientIdInfo.remainingCount() >= 0)
            {
                logger.info("clinetID: " + clientId + "  Calls count: " + clientIdInfo.getReqCount());
            }
            //Reset count if rate limit window is over
            else if (minutes >= rateLimitWindowInMinutes)
            {
                clientIdInfo.getReqCount().set(0);
                clientIdInfo.setLastCallTimeStamp(System.currentTimeMillis());
            }
            // return false when request meets rate limiting criteria
            else
                return false;
        }
        // Create new record of first request for each unique clientID request
        else
        {
            servletContext.setAttribute(clientId, new ClientId(new AtomicInteger(1), System.currentTimeMillis(),allowedRequestLimit));
        }

        return true;
    }

    @Override
    public long nextAvailableTime() {

        return TimeUnit.MINUTES.toSeconds(rateLimitWindowInMinutes) - seconds;
    }
}
