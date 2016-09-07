package br.com.concrete.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Phone {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private User user;

    private String number;
    private String ddd;

    @Deprecated
    public Phone(){
    }

    @JsonCreator
    public Phone(@JsonProperty("number") String number,@JsonProperty("ddd")  String ddd) {
        this.number = number;
        this.ddd = ddd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public String getDdd() {
        return ddd;
    }
}
