package com.shop.service.event;

import com.shop.service.test.TestDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventAppServiceImpl implements EventAppService {
    @Autowired
    TestDomainService testDomainService;

    @Override
    public String test(String str) {
        return testDomainService.testDomain(str);
    }
}
