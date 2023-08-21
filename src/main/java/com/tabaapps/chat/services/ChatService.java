package com.tabaapps.chat.services;

import com.tabaapps.chat.models.Friend;
import com.tabaapps.chat.models.Message;
import com.tabaapps.chat.models.Model;
import com.tabaapps.chat.models.User;
import com.tabaapps.chat.repositories.FriendRepository;
import com.tabaapps.chat.repositories.MessageRepository;
import com.tabaapps.chat.repositories.UserRepository;
import com.tabaapps.chat.security.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Friend addFriend(Long userId, User friend) throws BaseException {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        Friend newFriend = new Friend();
        newFriend.setUser(user);
        newFriend.setFriendsWith(friend);
        newFriend.setCreatedBy(friend);
        return this.friendRepository.save(newFriend);

    }

    public Friend acceptFriend(Long userId, Long with, boolean accepted) throws BaseException {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        User userFriend = this.userRepository.findById(with).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "Friend is not found"));
        Friend friend = this.friendRepository.findByUserAndFriendsWith(user, userFriend).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "Friendship does not exist"));
        friend.setAccepted(accepted);
        friend.setUpdatedBy(user);
        friend.setUpdatedAt(LocalDateTime.now());
        return this.friendRepository.save(friend);
    }

    public Long removeFriend(Long userId, Long with) throws BaseException {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        User userFriend = this.userRepository.findById(with).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "Friend is not found"));
        Friend friend = this.friendRepository.findByUserAndFriendsWith(user, userFriend).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "Friendship does not exist"));
        Long friendId = friend.getId();
        friendRepository.delete(friend);
        return friendId;
    }

    public Message sendMessage(Long fromUserId, Long toUserId, Message message) throws BaseException {
        User user = this.userRepository.findById(fromUserId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        User recipient = this.userRepository.findById(toUserId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "Recipient is not found"));
        message.setFrom(user);
        message.setTo(recipient);
        return this.messageRepository.save(message);
    }

    public Message replyMessage(Long messageId, Message repliedMessage) throws BaseException {
        Message message = this.messageRepository.findById(messageId).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "Message is not found"));
        List<Message> replies = message.getReplies();
        replies.add(repliedMessage);
        message.setUpdatedBy(message.getCreatedBy());
        message.setUpdatedAt(LocalDateTime.now());
        return this.messageRepository.save(message);
    }

    public Message updateMessage(Long id, Message updatedMessage) throws BaseException {
        Message message = this.messageRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "Message is not found"));
        message.setContent(updatedMessage.getContent());
        message.setUpdatedBy(message.getUpdatedBy());
        message.setUpdatedAt(LocalDateTime.now());
        return this.messageRepository.save(message);
    }

    public boolean readMessage(Long id) throws BaseException {
        Message message = this.messageRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "Message is not found"));
        message.setRead(true);
        message.setUpdatedBy(message.getTo());
        message.setUpdatedAt(LocalDateTime.now());
        this.messageRepository.save(message);
        return message.isRead();
    }

    public List<User> loadSuggestedFriends(Long id) throws Exception {
        List<Long> excludedUserIds = this.loadFriends(id).stream().map(friend -> {
            if (friend.getUser().getId().equals(id)){
                return friend.getFriendsWith().getId();
            }
            if (friend.getFriendsWith().getId().equals(id)){
                return friend.getUser().getId();
            }
            return null;
        }).collect(Collectors.toList());
        excludedUserIds.add(id);

        excludedUserIds.addAll(this.loadFriendRequests(id).stream().map(friend -> {
            if (friend.getUser().getId().equals(id)){
                return friend.getFriendsWith().getId();
            }
            if (friend.getFriendsWith().getId().equals(id)){
                return friend.getUser().getId();
            }
            return null;
        }).toList());
        return this.userRepository.findByIdNotIn(excludedUserIds);
    }

    public List<Friend> loadFriends(Long id) throws BaseException {
        User user = this.userRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        return this.friendRepository.findByUserOrFriendsWithAndAccepted(user, true);
    }

    public List<Friend> loadFriendRequests(Long id) throws BaseException {
        User user = this.userRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        return this.friendRepository.findByUserOrFriendsWithAndAccepted(user, false);
    }

    public List<Message> loadMessages(Long userId, Long friendId) throws Exception {
        User from = this.userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "User is not found"));
        User to = this.userRepository.findById(friendId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "Friend is not found"));
        List<Message> messages = new ArrayList<>();
        messages.addAll(this.messageRepository.findByFromAndTo(from, to));
        messages.addAll(this.messageRepository.findByFromAndTo(to, from));
        return messages.stream().sorted(Comparator.comparing(Model::getCreatedAt)).collect(Collectors.toList());
    }
}
