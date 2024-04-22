package com.svalero.apiEvent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(value = "events")
public class Event {
    @Id
    private String id;
    @Field
    private String code;
    @Field
    private String name;
    @Field
    private String description;

}
