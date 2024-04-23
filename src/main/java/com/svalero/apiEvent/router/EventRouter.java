package com.svalero.apiEvent.router;

import com.svalero.apiEvent.handler.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class EventRouter {

    @Bean
    public RouterFunction<ServerResponse> eventsRoute(EventHandler eventHandler) {
        return RouterFunctions
                .route(GET("/events").and(accept(MediaType.APPLICATION_JSON)), eventHandler::getAllEvents)
                .andRoute(GET("/event/{id}").and(accept(MediaType.APPLICATION_JSON)), eventHandler::getEvent)
                .andRoute(POST("/event").and(accept(MediaType.APPLICATION_JSON)), eventHandler::createEvent)
                .andRoute(DELETE("/event/{id}").and(accept(MediaType.APPLICATION_JSON)), eventHandler::deleteEvent)
                .andRoute(PUT("/event/{id}").and(accept(MediaType.APPLICATION_JSON)), eventHandler::updateEvent);
    }

}
