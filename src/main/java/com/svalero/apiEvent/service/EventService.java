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

    public Mono<Event> save(Event event) {
        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setCode(event.getCode());
        newEvent.setDescription(event.getDescription());
        return eventRepository.save(newEvent);
    }

    public Mono<Void> deleteEvent(String id) {
        return eventRepository.deleteById(id);
    }

    public Mono<Event> update(String id, Mono<Event> event) {
        return event.flatMap((p) ->
                eventRepository.findById(id).flatMap(existingEvent -> {
                    existingEvent.setCode(p.getCode());
                    existingEvent.setName(p.getName());
                    existingEvent.setDescription(p.getDescription());
                    return eventRepository.save(existingEvent);
                })
        );
    }
}
