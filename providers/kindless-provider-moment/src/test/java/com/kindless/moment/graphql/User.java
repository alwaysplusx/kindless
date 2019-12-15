package com.kindless.moment.graphql;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    Long userId;
    String[] hobby;
    List<Card> cards;
    Card card;

}
