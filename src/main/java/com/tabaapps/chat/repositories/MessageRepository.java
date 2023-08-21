package com.tabaapps.chat.repositories;

import com.tabaapps.chat.models.Message;
import com.tabaapps.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findByFromAndTo(User from, User to);
}
