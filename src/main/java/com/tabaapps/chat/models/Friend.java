package com.tabaapps.chat.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","friends_with"}))
public class Friend extends Model{


    public Friend(){
        this.accepted = false;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JsonProperty("friends_with")
    @JoinColumn(name = "friends_with")
    private User friendsWith;

    private boolean accepted;

}
