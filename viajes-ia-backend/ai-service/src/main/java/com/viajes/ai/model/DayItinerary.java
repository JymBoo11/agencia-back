package com.viajes.ai.model;

import java.util.List;

public class DayItinerary {
    private int day;
    private String title;
    private List<Activity> activities;
    private String accommodation;
    private String notes;

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> activities) { this.activities = activities; }

    public String getAccommodation() { return accommodation; }
    public void setAccommodation(String accommodation) { this.accommodation = accommodation; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}