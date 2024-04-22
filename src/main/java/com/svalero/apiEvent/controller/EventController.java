package com.svalero.apiEvent.controller;

import com.svalero.apiEvent.domain.Event;
import com.svalero.apiEvent.exception.EventNotFoundException;
import com.svalero.apiEvent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Event> getEvents() {
        return eventService.findAll().delayElements(Duration.of(1, ChronoUnit.SECONDS));
    }

    @PostMapping(value = "/event")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Event> addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    @DeleteMapping(value = "/event/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEvent(@PathVariable String eventId) {
        Mono<Event> event = eventService.findById(eventId);
        event.switchIfEmpty(Mono.error(new EventNotFoundException("Event not found")));
        return eventService.deleteEvent(eventId);
    }

    @ExceptionHandler
    public ServerResponse eventNotFoundException(EventNotFoundException bnfe) {
        return ServerResponse.notFound().build();
    }
}

