package com.dizstance.cloudfunctions.service;

import com.dizstance.cloudfunctions.model.Post;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {

    private List<Post> postList = new ArrayList<>();
    private final PlaceHolderInterface placeHolderInterface;

    public PostService(PlaceHolderInterface placeHolderInterface) {
        this.placeHolderInterface = placeHolderInterface;
    }

    @PostConstruct
    public void loadData() {
        log.info("Init loading data");
        postList = placeHolderInterface.loadData();
        log.info("The data was loaded");
    }

    public List<Post> getPosts() {
        return postList;
    }
}
