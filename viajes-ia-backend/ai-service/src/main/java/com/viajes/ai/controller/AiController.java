package com.viajes.ai.controller;

import com.viajes.ai.model.Conversation;
import com.viajes.ai.model.Recommendation;
import com.viajes.ai.service.ConversationService;
import com.viajes.ai.service.RecommendationEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ConversationService conversationService;
    private final RecommendationEngine recommendationEngine;

    public AiController(ConversationService conversationService, RecommendationEngine recommendationEngine) {
        this.conversationService = conversationService;
        this.recommendationEngine = recommendationEngine;
    }

    @PostMapping("/conversation/start")
    public ResponseEntity<Conversation> startConversation(@RequestParam Long userId) {
        return ResponseEntity.ok(conversationService.startConversation(userId));
    }

    @PostMapping("/conversation/{id}/answer")
    public ResponseEntity<Conversation> processAnswer(
            @PathVariable String id,
            @RequestBody AnswerRequest request) {

        Conversation conversation = conversationService.processAnswer(id, request.answer());

        if (conversation.getState() == Conversation.ConversationState.READY_FOR_RECOMMENDATION) {
            Recommendation recommendation = recommendationEngine.generateRecommendation(conversation);
            conversation.getMessages().add(new com.viajes.ai.model.Message("assistant",
                    "¡Aquí tienes tu recomendación para " + recommendation.getDestination() + "! " +
                    "Precio total estimado: €" + recommendation.getPriceBreakdown().getTotal()));
        }

        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/conversation/{id}")
    public ResponseEntity<Conversation> getConversation(@PathVariable String id) {
        return ResponseEntity.ok(conversationService.getConversation(id));
    }

    public record AnswerRequest(String answer) {}
}