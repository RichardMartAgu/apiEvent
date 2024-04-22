package com.svalero.apiEvent.service;

import com.svalero.apiEvent.domain.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
    Flux<Event> findAll();
    Mono<Event> findByCode(String code);
    Mono<Event> findById(String id);
    Mono<Event> addEvent(Event event);
    Mono<Void> deleteEvent(String busId);
}
