package com.tabaapps.chat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Setter
@Getter
public class Message extends Model{
    public Message(){
        this.setRead(false);
    }

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User to;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_read")
    private boolean read;

    @OneToMany
    @JoinTable(name = "message_reply",inverseJoinColumns = @JoinColumn(name = "replied_messaged_id"))
    private List<Message> replies;

    @JsonProperty("sent_time")
    public String getSentTime() {
        LocalDateTime now = LocalDateTime.now();
        int passedSeconds = this.createdAt.getSecond() - now.getSecond();
        if (this.createdAt.toLocalDate().equals(now.toLocalDate()) && this.createdAt.getMinute() == now.getMinute() && passedSeconds < 3){
            return "Now";
        } else if (this.createdAt.toLocalDate().equals(now.toLocalDate())){
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
            return createdAt.format(timeFormatter);
        } else if(this.createdAt.toLocalDate().equals(now.toLocalDate().minusDays(1))) {
            return "Yesterday";
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM");
        return createdAt.format(dateFormatter);
    }
}
