package br.com.concrete.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty
    private String message;

    public Message(String message) {
        this.message = message;
    }
}
