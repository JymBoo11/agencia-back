package com.viajes.ai.model;

public class Activity {
    private String time;
    private String description;
    private String location;
    private Double estimatedCost;
    private String category; // adventure, culture, relax, gastronomy

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}