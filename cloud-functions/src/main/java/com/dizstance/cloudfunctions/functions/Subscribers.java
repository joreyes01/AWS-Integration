package com.dizstance.cloudfunctions.functions;

import com.dizstance.cloudfunctions.model.Post;
import com.dizstance.cloudfunctions.model.Subscriber;
import com.dizstance.cloudfunctions.service.PostService;
import com.dizstance.cloudfunctions.service.ServiceSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class Subscribers {

    private final ServiceSubscriber serviceSubscriber;
    private final PostService postService;

    public Subscribers(ServiceSubscriber serviceSubscriber, PostService postService) {
        this.serviceSubscriber = serviceSubscriber;
        this.postService = postService;
    }

    @Bean//GET
    public Supplier<List<Subscriber>> findAll(){
        log.info("The method Subscriber.finAll() was called");
        return () -> serviceSubscriber.findAll();
    }

    @Bean//POST
    public Consumer<String> create() {
        return (email) -> serviceSubscriber.create(email);
    }

    @Bean
    public Supplier<List<Post>> findAllPosts() {
        log.info("The method Subscriber.findAllPosts() was called");
        return () -> postService.getPosts();
    }

    //GET
    //Por defecto el método retorna un JSON de la clase <T> indicada, en este caso POST
    //En caso de devolver un text, debería devolver un String
    @Bean
    public Supplier<Post> testResponseJson() {
        Post examplePost = new Post(1, "Example", "BodyExample");
        return () -> examplePost;
    }

    //Para este caso, se espera un Post dentro la clase Message<T>
    @Bean//POST whit Output
    public Function<Message<Post>, Message<String>> testRequestPost() {
        return (message) -> {
            Map<String, Object> headers = new HashMap<>();
            headers.put(MessageHeaders.CONTENT_TYPE, "application/json");
            MessageHeaders messageHeaders = new MessageHeaders(headers);

            log.info("Body of the request : " + message.getPayload().toString());
            return new GenericMessage<String>("Gilaso", messageHeaders);
        };
    }

}
