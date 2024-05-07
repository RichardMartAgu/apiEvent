package com.svalero.apiEvent.service;

import com.svalero.apiEvent.domain.Game;
import com.svalero.apiEvent.respository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public Flux<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Mono<Game> getGame(String id) {
        return gameRepository.findById(id);
    }

    public Mono<Game> save(Game game) {
        Game newGame = new Game();
        newGame.setMapName(game.getMapName());
        newGame.setPlayersInGame(game.getPlayersInGame());
        newGame.setPlayerWins(game.getPlayerWins());
        return gameRepository.save(newGame);
    }

    public Mono<Void> deleteGame(String id) {
        return gameRepository.deleteById(id);
    }

    public Mono<Game> update(String id, Mono<Game> game) {
        return game.flatMap((p) ->
                gameRepository.findById(id).flatMap(existingGame -> {
                    existingGame.setMapName(p.getMapName());
                    existingGame.setPlayersInGame(p.getPlayersInGame());
                    existingGame.setPlayerWins(p.getPlayerWins());
                    return gameRepository.save(existingGame);
                })
        );
    }
}
