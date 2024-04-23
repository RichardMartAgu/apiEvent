package com.svalero.apiEvent.handler;

import com.svalero.apiEvent.domain.Event;
import com.svalero.apiEvent.service.EventService;
import com.svalero.apiEvent.util.ErrorResponse;
import com.svalero.apiEvent.validator.EventValidator;
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

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class EventHandler {

    @Autowired
    private EventService eventService;

    private final Validator validator = new EventValidator();

    public Mono<ServerResponse> getAllEvents(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.getAllEvents(), Event.class);
    }

    public Mono<ServerResponse> getEvent(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return eventService.getEvent(id)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(p), Event.class))
                .switchIfEmpty(notFound(id));
    }

    public Mono<ServerResponse> createEvent(ServerRequest serverRequest) {
        Mono<Event> eventToSave = serverRequest.bodyToMono(Event.class)
                .doOnNext(this::validate);

        return eventToSave.flatMap(event ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(eventService.save(event), Event.class));
    }

    private void validate(Event event) {
        Errors errors = new BeanPropertyBindingResult(event, "event");
        validator.validate(event, errors);
        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }
    }

    public Mono<ServerResponse> deleteEvent(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return eventService.getEvent(id)
                .flatMap(event -> eventService.deleteEvent(id)
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(notFound(id));
    }

    public Mono<ServerResponse> updateEvent(ServerRequest serverRequest) {

        String eventId = serverRequest.pathVariable("id");
        Mono<Event> eventMono = serverRequest.bodyToMono(Event.class);
        return eventService.update(eventId, eventMono)
                .flatMap(updatedEvent ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(updatedEvent))
                .switchIfEmpty(notFound(eventId));
    }


    private Mono<ServerResponse> notFound(){
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ErrorResponse(404,"Event not found")),ErrorResponse.class);
    }
    private Mono<ServerResponse> notFound(String id){
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ErrorResponse(404,"Event Id: "+id+" -> not found ")),ErrorResponse.class);
    }
}
