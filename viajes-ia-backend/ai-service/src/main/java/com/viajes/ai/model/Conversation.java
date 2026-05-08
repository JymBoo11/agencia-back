package com.viajes.ai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "conversations")
public class Conversation {
    @Id
    private String id;
    private Long userId;
    private List<Message> messages = new ArrayList<>();
    private TripPreferences preferences = new TripPreferences();
    private ConversationState state = ConversationState.GATHERING_INFO;
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum ConversationState {
        GATHERING_INFO, READY_FOR_RECOMMENDATION, COMPLETED
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    public TripPreferences getPreferences() { return preferences; }
    public void setPreferences(TripPreferences preferences) { this.preferences = preferences; }

    public ConversationState getState() { return state; }
    public void setState(ConversationState state) { this.state = state; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}