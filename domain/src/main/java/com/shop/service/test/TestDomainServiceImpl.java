package com.shop.service.test;

import org.springframework.stereotype.Service;

@Service
public class TestDomainServiceImpl implements TestDomainService{
    @Override
    public String testDomain(String txt) {
        return "Hello fix cung";
    }
}
