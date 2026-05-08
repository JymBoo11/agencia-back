package com.viajes.ai.service;

import com.viajes.ai.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationEngine {

    public Recommendation generateRecommendation(Conversation conversation) {
        TripPreferences prefs = conversation.getPreferences();
        Recommendation recommendation = new Recommendation();

        if ("sorpresa".equalsIgnoreCase(prefs.getDestination()) || prefs.getDestination() == null) {
            recommendation.setDestination(determineDestinationByInterests(prefs));
        } else {
            recommendation.setDestination(prefs.getDestination());
        }

        recommendation.setDestinationDescription(getDestinationDescription(recommendation.getDestination()));
        recommendation.setItinerary(generateItinerary(prefs, recommendation.getDestination()));
        recommendation.setPriceBreakdown(calculatePrices(prefs));
        recommendation.setRecommendedActivities(getActivitiesForDestination(recommendation.getDestination(), prefs.getInterests()));
        recommendation.setBestTimeToVisit(getBestTimeToVisit(recommendation.getDestination()));
        recommendation.setClimate(getClimate(recommendation.getDestination()));

        return recommendation;
    }

    private String determineDestinationByInterests(TripPreferences prefs) {
        if (prefs.getInterests() == null) return "Tokyo";

        List<String> interests = prefs.getInterests();

        if (interests.contains("relax") && interests.contains("beach")) return "Maldivas";
        if (interests.contains("culture") && interests.contains("history")) return "Roma";
        if (interests.contains("adventure")) return "Costa Rica";
        if (interests.contains("gastronomy")) return "París";
        if (interests.contains("nightlife")) return "Ibiza";
        if (interests.contains("nature")) return "Costa Rica";
        if (interests.contains("shopping")) return "Dubai";

        return "Barcelona";
    }

    private String getDestinationDescription(String destination) {
        switch (destination.toLowerCase()) {
            case "tokyo": return "Una metrópolis vibrante donde la tradición se encuentra con el futuro.";
            case "roma": return "La Ciudad Eterna, cuna del Imperio Romano y el Renacimiento.";
            case "parís": return "La Ciudad de la Luz, famosa por su gastronomía y arte.";
            case "barcelona": return "Una ciudad mediterránea con arquitectura única de Gaudí.";
            case "costa rica": return "Paraíso natural con selvas, volcanes y playas increíbles.";
            case "maldivas": return "Paraíso tropical con aguas cristalinas y resorts de lujo.";
            case "ibiza": return "La capital mundial de la música electrónica y vida nocturna.";
            case "dubai": return "Ciudad futurista en el desierto con compras de lujo.";
            default: return "Un destino increíble esperándote.";
        }
    }

    private List<DayItinerary> generateItinerary(TripPreferences prefs, String destination) {
        List<DayItinerary> itinerary = new ArrayList<>();
        long days = prefs.getStartDate().until(prefs.getEndDate()).getDays();

        for (int i = 0; i < Math.max(days, 3); i++) {
            DayItinerary day = new DayItinerary();
            day.setDay(i + 1);
            day.setTitle("Día " + (i + 1) + " en " + destination);
            day.setActivities(generateActivitiesForDay(destination, prefs.getInterests(), i));
            day.setAccommodation("Hotel recomendado en zona céntrica");
            day.setNotes("Recuerda llevar calzado cómodo");
            itinerary.add(day);
        }

        return itinerary;
    }

    private List<Activity> generateActivitiesForDay(String destination, List<String> interests, int dayIndex) {
        List<Activity> activities = new ArrayList<>();

        if (dayIndex == 0) {
            activities.add(createActivity("09:00", "Llegada y check-in en hotel", "Centro", 0.0, "arrival"));
            activities.add(createActivity("12:00", "Almuerzo de bienvenida", "Restaurante local", 25.0, "gastronomy"));
        }

        if (interests != null) {
            if (interests.contains("culture")) {
                activities.add(createActivity("10:00", "Visita a museo principal", "Museo", 15.0, "culture"));
            }
            if (interests.contains("adventure")) {
                activities.add(createActivity("08:00", "Excursión al aire libre", "Naturaleza", 50.0, "adventure"));
            }
            if (interests.contains("relax")) {
                activities.add(createActivity("16:00", "Tiempo libre para descansar", "Hotel", 0.0, "relax"));
            }
        }

        activities.add(createActivity("14:00", "Exploración por cuenta propia", "Ciudad", 20.0, "exploration"));
        activities.add(createActivity("20:00", "Cena típica", "Restaurante", 35.0, "gastronomy"));

        return activities;
    }

    private Activity createActivity(String time, String desc, String loc, Double cost, String cat) {
        Activity a = new Activity();
        a.setTime(time);
        a.setDescription(desc);
        a.setLocation(loc);
        a.setEstimatedCost(cost);
        a.setCategory(cat);
        return a;
    }

    private PriceBreakdown calculatePrices(TripPreferences prefs) {
        PriceBreakdown breakdown = new PriceBreakdown();
        int travelers = prefs.getTravelers() != null ? prefs.getTravelers() : 1;

        double flightBase = getFlightPrice(prefs.getBudget()) * travelers;
        double hotelBase = getHotelPrice(prefs.getBudget()) * getTripDays(prefs) * travelers;
        double activitiesBase = getActivitiesPrice(prefs.getBudget()) * getTripDays(prefs);
        double insuranceBase = 30.0 * travelers;
        double carBase = prefs.getNeeds() != null && prefs.getNeeds().contains("car") ? 40.0 * getTripDays(prefs) : 0.0;

        breakdown.setFlights(flightBase);
        breakdown.setAccommodation(hotelBase);
        breakdown.setActivities(activitiesBase);
        breakdown.setInsurance(insuranceBase);
        breakdown.setCarRental(carBase);
        breakdown.setTotal(flightBase + hotelBase + activitiesBase + insuranceBase + carBase);
        breakdown.setCurrency("EUR");

        return breakdown;
    }

    private double getFlightPrice(TripPreferences.BudgetRange budget) {
        if (budget == null) return 300;
        return switch (budget) {
            case LOW -> 200;
            case MEDIUM -> 400;
            case HIGH -> 800;
            case LUXURY -> 2000;
        };
    }

    private double getHotelPrice(TripPreferences.BudgetRange budget) {
        if (budget == null) return 80;
        return switch (budget) {
            case LOW -> 50;
            case MEDIUM -> 120;
            case HIGH -> 250;
            case LUXURY -> 500;
        };
    }

    private double getActivitiesPrice(TripPreferences.BudgetRange budget) {
        if (budget == null) return 30;
        return switch (budget) {
            case LOW -> 20;
            case MEDIUM -> 50;
            case HIGH -> 100;
            case LUXURY -> 200;
        };
    }

    private int getTripDays(TripPreferences prefs) {
        if (prefs.getStartDate() == null || prefs.getEndDate() == null) return 3;
        return (int) prefs.getStartDate().until(prefs.getEndDate()).getDays();
    }

    private List<String> getActivitiesForDestination(String destination, List<String> interests) {
        List<String> base = new ArrayList<>();
        base.add("Visita a lugares emblemáticos");
        base.add("Gastronomía local");

        if (interests != null) {
            if (interests.contains("adventure")) base.add("Senderismo y aventura");
            if (interests.contains("culture")) base.add("Museos y sitios históricos");
            if (interests.contains("relax")) base.add("Spa y relax");
        }

        return base;
    }

    private String getBestTimeToVisit(String destination) {
        return "Primavera y otoño suelen ser las mejores épocas.";
    }

    private String getClimate(String destination) {
        return "Clima templado-mediterráneo.";
    }
}