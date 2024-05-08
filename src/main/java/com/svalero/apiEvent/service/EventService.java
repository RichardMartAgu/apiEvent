package com.svalero.apiEvent.service;

import com.svalero.apiEvent.domain.Event;
import com.svalero.apiEvent.respository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Flux<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Mono<Event> getEvent(String id) {
        return eventRepository.findById(id);
    }

    public Mono<Event> createEvent(Event event) {
        Event newEvent = new Event();
        newEvent.setState(event.getState());
        newEvent.setCode(event.getCode());
        newEvent.setDescription(event.getDescription());
        newEvent.setGameId(event.getGameId());
        return eventRepository.save(newEvent);
    }

    public Mono<Void> deleteEvent(String id) {
        return eventRepository.deleteById(id);
    }

    public Mono<Event> update(String id, Mono<Event> event) {
        return event.flatMap((p) ->
                eventRepository.findById(id).flatMap(existingEvent -> {
                    existingEvent.setCode(p.getCode());
                    existingEvent.setState(p.getState());
                    existingEvent.setDescription(p.getDescription());
                    existingEvent.setGameId(p.getGameId());
                    return eventRepository.save(existingEvent);
                })
        );
    }

    public Flux<Event> getEventsByGameId(String gameId) {
        return eventRepository.findByGameId(gameId);
    }
}
