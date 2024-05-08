package com.svalero.apiEvent.respository;

import com.svalero.apiEvent.domain.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventRepository extends ReactiveMongoRepository<Event, String> {
    Flux<Event> findByGameId(String juegoId);
}
