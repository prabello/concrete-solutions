package br.com.concrete.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public Phone(String number, String ddd) {
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
