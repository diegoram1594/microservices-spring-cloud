package com.microservices.limitsservice;

import com.microservices.limitsservice.beans.LimitsConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;
    @GetMapping("/limits")
    public LimitsConfiguration retrieveLimitsConfiguration(){
        return new LimitsConfiguration(configuration.getMaximum(),configuration.getMinimum());
    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    public LimitsConfiguration retrieveConfiguration(){
        throw new RuntimeException("Not Available");
    }

    public LimitsConfiguration fallbackRetrieveConfiguration(){
        return new LimitsConfiguration(100,1);
    }
}
