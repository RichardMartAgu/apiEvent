package com.svalero.apiEvent.respository;

import com.svalero.apiEvent.domain.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository extends ReactiveMongoRepository<Event, String> {

    Flux<Event> findAll();
    Mono<Event> findByCode(String code);
}
