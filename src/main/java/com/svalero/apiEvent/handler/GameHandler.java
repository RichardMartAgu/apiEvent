package com.svalero.apiEvent.handler;

import com.svalero.apiEvent.domain.Game;
import com.svalero.apiEvent.service.GameService;
import com.svalero.apiEvent.util.ErrorResponse;
import com.svalero.apiEvent.validator.GameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class GameHandler {

    @Autowired
    private GameService gameService;

    private final Validator validator = new GameValidator();

    public Mono<ServerResponse> getAllGames(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gameService.getAllGames(), Game.class);
    }

    public Mono<ServerResponse> getGame(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return gameService.getGame(id)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(p), Game.class))
                .switchIfEmpty(notFound(id));
    }

    public Mono<ServerResponse> createGame(ServerRequest serverRequest) {
        Mono<Game> gameToSave = serverRequest.bodyToMono(Game.class)
                .doOnNext(this::validate);

        return gameToSave.flatMap(game ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(gameService.createGame(game), Game.class));
    }

    private void validate(Game game) {
        Errors errors = new BeanPropertyBindingResult(game, "game");
        validator.validate(game, errors);
        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }
    }

    public Mono<ServerResponse> deleteGame(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return gameService.getGame(id)
                .flatMap(game -> gameService.deleteGame(id)
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(notFound(id));
    }

    public Mono<ServerResponse> updateGame(ServerRequest serverRequest) {

        String gameId = serverRequest.pathVariable("id");
        Mono<Game> gameMono = serverRequest.bodyToMono(Game.class);
        return gameService.update(gameId, gameMono)
                .flatMap(updatedGame ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(updatedGame))
                .switchIfEmpty(notFound(gameId));
    }


    private Mono<ServerResponse> notFound() {
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ErrorResponse(404, "Game not found")), ErrorResponse.class);
    }

    private Mono<ServerResponse> notFound(String id) {
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ErrorResponse(404, "Game Id: " + id + " -> not found ")), ErrorResponse.class);
    }
}

