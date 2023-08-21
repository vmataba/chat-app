package com.tabaapps.chat.repositories;

import com.tabaapps.chat.models.Friend;
import com.tabaapps.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
    Optional<Friend> findByUserAndFriendsWith(User user, User friendsWith);
    @Query( value = "SELECT f FROM Friend f WHERE (f.user = :user OR f.friendsWith = :user) AND f.accepted = :accepted")
    List<Friend> findByUserOrFriendsWithAndAccepted(User user, boolean accepted);

}
