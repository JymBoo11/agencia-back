package com.viajes.ai.service;

import com.viajes.ai.model.Conversation;
import com.viajes.ai.model.Question;
import com.viajes.ai.model.TripPreferences;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class QuestionGenerator {

    public Question generateNextQuestion(Conversation conversation) {
        TripPreferences prefs = conversation.getPreferences();

        if (prefs.getOrigin() == null) {
            return new Question("origin", "¿Desde qué ciudad quieres viajar?", "text");
        }
        if (prefs.getDestination() == null) {
            return new Question("destination", "¿Tienes algún destino en mente? (puedes decir 'sorpresa')", "text");
        }
        if (prefs.getStartDate() == null) {
            return new Question("startDate", "¿Cuándo quieres empezar el viaje?", "date");
        }
        if (prefs.getEndDate() == null) {
            return new Question("endDate", "¿Cuándo quieres regresar?", "date");
        }
        if (prefs.getTravelers() == null || prefs.getTravelers() == 0) {
            return new Question("travelers", "¿Cuántas personas viajarán?", "select",
                    Arrays.asList("1", "2", "3", "4", "5+"));
        }
        if (prefs.getBudget() == null) {
            return new Question("budget", "¿Cuál es tu presupuesto aproximado por persona?", "select",
                    Arrays.asList("LOW", "MEDIUM", "HIGH", "LUXURY"));
        }
        if (prefs.getInterests() == null || prefs.getInterests().isEmpty()) {
            return new Question("interests", "¿Qué tipo de experiencias buscas?", "multiselect",
                    Arrays.asList("relax", "adventure", "culture", "gastronomy", "nightlife", "nature", "shopping"));
        }
        if (prefs.getNeeds() == null || prefs.getNeeds().isEmpty()) {
            return new Question("needs", "¿Qué necesitas que gestionemos?", "multiselect",
                    Arrays.asList("flights", "hotel", "car", "activities", "insurance", "transfers"));
        }

        return null;
    }

    public String getWelcomeMessage() {
        return "¡Hola! Soy tu asistente de viajes con IA. Te ayudaré a planificar el viaje perfecto. " +
                "Haré algunas preguntas para entender lo que buscas.";
    }

    public String getProcessCompleteMessage() {
        return "¡Perfecto! Tengo toda la información que necesito. Déjame generar tu itinerario personalizado...";
    }
}