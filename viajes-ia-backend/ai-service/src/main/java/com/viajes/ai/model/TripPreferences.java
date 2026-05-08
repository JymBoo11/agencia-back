package com.viajes.ai.model;

import java.time.LocalDate;
import java.util.List;

public class TripPreferences {
    private String origin;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetRange budget;
    private List<String> interests;
    private List<String> needs;
    private Integer travelers = 1;

    public enum BudgetRange {
        LOW, MEDIUM, HIGH, LUXURY
    }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BudgetRange getBudget() { return budget; }
    public void setBudget(BudgetRange budget) { this.budget = budget; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    public List<String> getNeeds() { return needs; }
    public void setNeeds(List<String> needs) { this.needs = needs; }

    public Integer getTravelers() { return travelers; }
    public void setTravelers(Integer travelers) { this.travelers = travelers; }
}