package com.svalero.apiEvent.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("events")
public class Event implements Serializable {
    @Id
    private String id;
    @Field
    private String code;
    @Field
    private String state;
    @Field
    private String description;
    @Field
    private String gameId;

}
