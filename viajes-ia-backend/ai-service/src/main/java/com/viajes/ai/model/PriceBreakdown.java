package com.viajes.ai.model;

import java.util.List;

public class PriceBreakdown {
    private Double flights;
    private Double accommodation;
    private Double activities;
    private Double insurance;
    private Double carRental;
    private Double total;
    private String currency = "EUR";
    private List<PriceItem> itemizedList;

    public static class PriceItem {
        private String description;
        private Double amount;
        private String category;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }

    public Double getFlights() { return flights; }
    public void setFlights(Double flights) { this.flights = flights; }

    public Double getAccommodation() { return accommodation; }
    public void setAccommodation(Double accommodation) { this.accommodation = accommodation; }

    public Double getActivities() { return activities; }
    public void setActivities(Double activities) { this.activities = activities; }

    public Double getInsurance() { return insurance; }
    public void setInsurance(Double insurance) { this.insurance = insurance; }

    public Double getCarRental() { return carRental; }
    public void setCarRental(Double carRental) { this.carRental = carRental; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<PriceItem> getItemizedList() { return itemizedList; }
    public void setItemizedList(List<PriceItem> itemizedList) { this.itemizedList = itemizedList; }
}