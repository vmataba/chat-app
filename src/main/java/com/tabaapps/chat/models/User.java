package com.tabaapps.chat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tabaapps.chat.security.SecurityConfiguration;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class User extends Model {

    @Column(nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("middle_name")
    private String middleName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonProperty("profile_image")
    private String profileImage;

    private boolean online;

    @ManyToMany
    @JoinTable(name = "friend",inverseJoinColumns = @JoinColumn(name = "friends_with"))
    private List<User> friends;

    public void setPassword(String password) {
        this.password = SecurityConfiguration.passwordEncoder.encode(password);
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public boolean friendsWith(User user){
        return this.getFriends().stream().anyMatch(friend -> friend.getId().equals(user.getId()));
    }

    @JsonIgnore
    public List<User> getFriends() {
        return friends;
    }
}
