package com.shop.resource;

import com.shop.constants.ResilienceNames;
import com.shop.service.event.EventAppService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.nodes.Tag;

import java.security.SecureRandom;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private EventAppService eventAppService;

    @Autowired
    private RestTemplate restTemplate;

    @RateLimiter(name = ResilienceNames.HELLO_API,fallbackMethod = "fallbackHello")
    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) Optional<String> name) {
        return eventAppService.test(name.orElse(null));
    }

    private String fallbackHello(Throwable e) {
        return "Server ALoooo 500 Internal";
    }


    private String fallbackHelloForCir(Throwable e) {
        return "Server Cir 500 Internal";
    }


    @CircuitBreaker(name = ResilienceNames.HELLO_API, fallbackMethod = "fallbackHelloForCir")
    @GetMapping("/cir")
    public Object cirBreaker() {
        SecureRandom rd = new SecureRandom();
        String url = "https://fakestoreapi.com/products/";//  + rd.nextInt(20) + 1;
        String url7 = "https://fakestoreapi.com/products/7";
        int random = (int) (Math.random() * 10) + 1;
        String urlX = url + random;
        log.info(urlX);
        Object data = restTemplate.getForObject(urlX, String.class);
        return data;
    }
}
