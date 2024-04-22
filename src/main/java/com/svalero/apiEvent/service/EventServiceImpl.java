package com.svalero.apiEvent.service;

import com.svalero.apiEvent.domain.Event;
import com.svalero.apiEvent.respository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;


    @Override
    public Flux<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Mono<Event> findByCode(String code) {
        return eventRepository.findByCode(code);
    }

    @Override
    public Mono<Event> findById(String id) {
        return eventRepository.findById(id);
    }

    @Override
    public Mono<Event> addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Mono<Void> deleteEvent(String eventId) {
        Mono<Event> event = findById(eventId).cache();
        return eventRepository.deleteById(eventId);
    }
}
