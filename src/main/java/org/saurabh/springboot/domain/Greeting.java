package org.saurabh.springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {

    @JsonProperty
    private final long id;
    @JsonProperty
    private final String message;

    public Greeting(long id, String message) {
        this.id = id;
        this.message = message;
    }
}
