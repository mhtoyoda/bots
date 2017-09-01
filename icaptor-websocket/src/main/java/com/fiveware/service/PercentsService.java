package com.fiveware.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by valdisnei on 7/11/17.
 */
@Service
public class PercentsService {

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public Integer percents(){
        return atomicInteger.incrementAndGet();
    }
}
