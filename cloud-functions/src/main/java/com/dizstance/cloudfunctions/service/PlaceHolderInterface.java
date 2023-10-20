package com.dizstance.cloudfunctions.service;

import com.dizstance.cloudfunctions.model.Post;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface PlaceHolderInterface {
    @GetExchange("/posts")
    List<Post> loadData();

}
