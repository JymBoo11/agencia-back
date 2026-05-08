package com.viajes.ai.model;

import java.util.List;

public class Recommendation {
    private String destination;
    private String destinationDescription;
    private List<DayItinerary> itinerary;
    private PriceBreakdown priceBreakdown;
    private List<String> recommendedActivities;
    private String bestTimeToVisit;
    private String climate;

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDestinationDescription() { return destinationDescription; }
    public void setDestinationDescription(String destinationDescription) { this.destinationDescription = destinationDescription; }

    public List<DayItinerary> getItinerary() { return itinerary; }
    public void setItinerary(List<DayItinerary> itinerary) { this.itinerary = itinerary; }

    public PriceBreakdown getPriceBreakdown() { return priceBreakdown; }
    public void setPriceBreakdown(PriceBreakdown priceBreakdown) { this.priceBreakdown = priceBreakdown; }

    public List<String> getRecommendedActivities() { return recommendedActivities; }
    public void setRecommendedActivities(List<String> recommendedActivities) { this.recommendedActivities = recommendedActivities; }

    public String getBestTimeToVisit() { return bestTimeToVisit; }
    public void setBestTimeToVisit(String bestTimeToVisit) { this.bestTimeToVisit = bestTimeToVisit; }

    public String getClimate() { return climate; }
    public void setClimate(String climate) { this.climate = climate; }
}