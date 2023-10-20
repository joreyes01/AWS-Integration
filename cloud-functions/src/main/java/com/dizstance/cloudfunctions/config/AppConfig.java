package com.dizstance.cloudfunctions.config;

import com.dizstance.cloudfunctions.functions.Subscribers;
import com.dizstance.cloudfunctions.service.PlaceHolderInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
//@Import(Subscribers.class)
public class AppConfig {

    @Bean
    public PlaceHolderInterface placeHolderInterface() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build()
                .createClient(PlaceHolderInterface.class);
    }

}
