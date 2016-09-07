package br.com.concrete.controller;

import br.com.concrete.model.Phone;
import br.com.concrete.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserBuilder {

    private String email = "exemplo@exemplo.com.br";
    private String password = "ovelha";
    private Calendar lastLogin = Calendar.getInstance();
    private String name = "Usuario exemplo";
    private List<Phone> phones = new ArrayList<>();

    public User build() {
        User user = new User(email);
        user.setPassword(password);
        user.setName(name);
        user.setLastLogin(lastLogin);
        user.setPhones(phones);
        return user;
    }

    public UserBuilder password(String password){
        this.password = password;
        return this;
    }

    public UserBuilder lastLogin(Calendar lastLogin){
        this.lastLogin = lastLogin;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder name(String name){
        this.name = name;
        return this;
    }

    public UserBuilder addPhone(Phone phone){
        phones.add(phone);
        return this;
    }
}
