package com.viajes.ai.service;

import com.viajes.ai.model.Conversation;
import com.viajes.ai.model.Message;
import com.viajes.ai.model.Question;
import com.viajes.ai.model.TripPreferences;
import com.viajes.ai.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final QuestionGenerator questionGenerator;
    private final RecommendationEngine recommendationEngine;

    public ConversationService(ConversationRepository conversationRepository,
                               QuestionGenerator questionGenerator,
                               RecommendationEngine recommendationEngine) {
        this.conversationRepository = conversationRepository;
        this.questionGenerator = questionGenerator;
        this.recommendationEngine = recommendationEngine;
    }

    public Conversation startConversation(Long userId) {
        Conversation conversation = new Conversation();
        conversation.setUserId(userId);
        conversation.setMessages(List.of(new Message("assistant", questionGenerator.getWelcomeMessage())));

        Question firstQuestion = questionGenerator.generateNextQuestion(conversation);
        if (firstQuestion != null) {
            conversation.getMessages().add(new Message("assistant", firstQuestion.getText()));
        }

        conversation.setUpdatedAt(java.time.LocalDateTime.now());
        return conversationRepository.save(conversation);
    }

    public Conversation processAnswer(String conversationId, String answer) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        conversation.getMessages().add(new Message("user", answer));
        updatePreferences(conversation, answer);

        Question nextQuestion = questionGenerator.generateNextQuestion(conversation);

        if (nextQuestion != null) {
            conversation.getMessages().add(new Message("assistant", nextQuestion.getText()));
        } else {
            conversation.setState(Conversation.ConversationState.READY_FOR_RECOMMENDATION);
            conversation.getMessages().add(new Message("assistant", questionGenerator.getProcessCompleteMessage()));
        }

        conversation.setUpdatedAt(java.time.LocalDateTime.now());
        return conversationRepository.save(conversation);
    }

    public Conversation getConversation(String id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    private void updatePreferences(Conversation conversation, String answer) {
        var prefs = conversation.getPreferences();

        if (prefs.getOrigin() == null) {
            prefs.setOrigin(answer);
        } else if (prefs.getDestination() == null) {
            prefs.setDestination(answer);
        } else if (prefs.getStartDate() == null) {
            try {
                prefs.setStartDate(LocalDate.parse(answer));
            } catch (Exception e) {
                throw new RuntimeException("Formato de fecha inválido. Usa YYYY-MM-DD");
            }
        } else if (prefs.getEndDate() == null) {
            try {
                prefs.setEndDate(LocalDate.parse(answer));
            } catch (Exception e) {
                throw new RuntimeException("Formato de fecha inválido. Usa YYYY-MM-DD");
            }
        } else if (prefs.getTravelers() == null || prefs.getTravelers() == 0) {
            try {
                prefs.setTravelers(Integer.parseInt(answer));
            } catch (NumberFormatException e) {
                prefs.setTravelers(1);
            }
        } else if (prefs.getBudget() == null) {
            try {
                prefs.setBudget(TripPreferences.BudgetRange.valueOf(answer));
            } catch (IllegalArgumentException e) {
                prefs.setBudget(TripPreferences.BudgetRange.MEDIUM);
            }
        } else if (prefs.getInterests() == null || prefs.getInterests().isEmpty()) {
            prefs.setInterests(Arrays.asList(answer.split(",")));
        } else if (prefs.getNeeds() == null || prefs.getNeeds().isEmpty()) {
            prefs.setNeeds(Arrays.asList(answer.split(",")));
        }
    }
}