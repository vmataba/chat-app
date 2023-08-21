package com.tabaapps.chat.controllers;

import com.tabaapps.chat.models.Friend;
import com.tabaapps.chat.models.Message;
import com.tabaapps.chat.models.User;
import com.tabaapps.chat.security.BaseException;
import com.tabaapps.chat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping(path = "/load-suggested-friends/{userId}")
    public ResponseEntity<?> loadSuggestedFriends(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(this.chatService.loadSuggestedFriends(userId));
    }

    @GetMapping(path = "/load-friend-requests/{userId}")
    public ResponseEntity<?> loadFriendRequests(@PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(this.chatService.loadFriendRequests(userId));
    }

    @GetMapping(path = "/load-friends/{userId}")
    public ResponseEntity<?> loadFriends(@PathVariable Long userId) throws BaseException {
        return ResponseEntity.ok(this.chatService.loadFriends(userId));
    }

    @PostMapping(path = "/add-friend/{userId}")
    public ResponseEntity<?> addFriend(@PathVariable Long userId, @RequestBody User friend) throws Exception{
        return ResponseEntity.ok(this.chatService.addFriend(userId,friend));
    }

    @PostMapping(path = "/accept-friend/{userId}/{friendId}")
    public ResponseEntity<?> acceptFriend(@PathVariable Long userId,@PathVariable Long friendId, @RequestBody Friend friend) throws Exception{
        return ResponseEntity.ok(this.chatService.acceptFriend(userId,friendId,friend.isAccepted()));
    }

    @GetMapping(path = "/remove-friend/{userId}/{friendId}")
    public ResponseEntity<?> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) throws Exception{
        return ResponseEntity.ok(this.chatService.removeFriend(userId,friendId));
    }

    @PostMapping(path = "/send-message/{fromUserId}/{toUserId}")
    public ResponseEntity<?> sendMessage(@PathVariable Long fromUserId, @PathVariable Long toUserId, @RequestBody Message message) throws Exception{
        return ResponseEntity.ok(this.chatService.sendMessage(fromUserId,toUserId,message));
    }

    @PostMapping(path = "/reply-message/{messageId}")
    public ResponseEntity<?> replyMessage(@PathVariable Long messageId, @RequestBody Message message) throws Exception{
        return ResponseEntity.ok(this.chatService.replyMessage(messageId,message));
    }

    @PutMapping(path = "/update-message/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Long messageId, @RequestBody Message message) throws Exception{
        return ResponseEntity.ok(this.chatService.updateMessage(messageId,message));
    }

    @PutMapping(path = "/read-message/{messageId}")
    public ResponseEntity<?> readMessage(@PathVariable Long messageId) throws Exception{
        return ResponseEntity.ok(this.chatService.readMessage(messageId));
    }

    @GetMapping(path = "/load-messages/{userId}/{friendId}")
    public ResponseEntity<?> loadMessages(@PathVariable Long userId, @PathVariable Long friendId) throws Exception{
        return ResponseEntity.ok(this.chatService.loadMessages(userId,friendId));
    }

}
