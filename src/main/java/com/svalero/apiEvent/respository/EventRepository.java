package com.svalero.apiEvent.respository;

import com.svalero.apiEvent.domain.Event;
import com.svalero.apiEvent.domain.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface EventRepository extends ReactiveMongoRepository<Event, String> {
    Flux<Event> findByGameId(String juegoId);
}
