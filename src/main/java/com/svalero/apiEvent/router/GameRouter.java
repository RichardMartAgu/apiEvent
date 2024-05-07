package com.svalero.apiEvent.router;

import com.svalero.apiEvent.handler.EventHandler;
import com.svalero.apiEvent.handler.GameHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class GameRouter {

    @Bean
    public RouterFunction<ServerResponse> gamesRoute(GameHandler gameHandler) {
        return RouterFunctions
                .route(GET("/games").and(accept(MediaType.APPLICATION_JSON)), gameHandler::getAllGames)
                .andRoute(GET("/game/{id}").and(accept(MediaType.APPLICATION_JSON)), gameHandler::getGame)
                .andRoute(POST("/game").and(accept(MediaType.APPLICATION_JSON)), gameHandler::createGame)
                .andRoute(DELETE("/game/{id}").and(accept(MediaType.APPLICATION_JSON)), gameHandler::deleteGame)
                .andRoute(PUT("/game/{id}").and(accept(MediaType.APPLICATION_JSON)), gameHandler::updateGame);
    }

}
