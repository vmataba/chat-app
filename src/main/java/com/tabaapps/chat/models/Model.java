package com.tabaapps.chat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class Model {

    Model(){
        this.setCreatedAt(LocalDateTime.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonProperty("created_by")
    private User createdBy;

    @JsonProperty("created_at")
    protected LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by")
    @JsonProperty("updated_by")
    private User updatedBy;

    @JsonIgnore
    @JsonProperty("updated_at")
    protected LocalDateTime updatedAt;
}
