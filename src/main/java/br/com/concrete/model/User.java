package br.com.concrete.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @JsonProperty("id")
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy'T'HH:mm:ss")
    private Calendar created;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy'T'HH:mm:ss")
    private Calendar modified;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy'T'HH:mm:ss")
    private Calendar lastLogin;

    @Column(unique = true)
    private String token;

    private String name;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @Transient
    @JsonIgnore
    private String plainPassword;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Phone> phones;

    public User() {
        this.uuid = UUID.randomUUID().toString();
        this.created = Calendar.getInstance();
        this.token = UUID.randomUUID().toString();
    }

    public User(String email){
        this();
        this.email = email;
    }

    @PrePersist
    public void onSave(){
        hashPassword();
    }

    private void hashPassword() {
        this.password = new BCryptPasswordEncoder().encode(this.plainPassword);
    }

    @JsonSetter(value = "password")
    public void setPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public boolean isPasswordEquals(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, this.password);
    }

    public boolean isPasswordEquals(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(user.plainPassword,this.password);
    }

    @PreUpdate
    public void onUpdate(){
        this.modified = Calendar.getInstance();
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Calendar getCreated() {
        return created;
    }

    public Calendar getModified() {
        return modified;
    }

    public Calendar getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Calendar lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        if(password == null || password.isEmpty()){
            return plainPassword;
        }
        return password;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @JsonIgnore
    public boolean isLoggedIn() {
        LocalDateTime lastLogin = LocalDateTime.ofInstant(this.lastLogin.toInstant(), ZoneId.systemDefault());
        return ChronoUnit.MINUTES.between(lastLogin, LocalDateTime.now()) < 30;
    }
}
