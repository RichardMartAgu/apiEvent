package com.svalero.apiEvent.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("games")
public class Game {
    @Id
    private String id;
    @Field
    private String mapName;
    @Field
    private int playersInGame;
    @Field
    private String playerWins;

}
