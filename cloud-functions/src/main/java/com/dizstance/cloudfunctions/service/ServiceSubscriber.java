package com.dizstance.cloudfunctions.service;

import com.dizstance.cloudfunctions.model.Subscriber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ServiceSubscriber {

    private List<Subscriber> subscribers = new ArrayList<>();
    //Valores autoincrementados con AtomicInteger
    private AtomicInteger id = new AtomicInteger(0);

    public List<Subscriber> findAll() {
        return subscribers;
    }

    public void create(String email) {
        subscribers.add(new Subscriber(id.addAndGet(1), email));
    }

}
