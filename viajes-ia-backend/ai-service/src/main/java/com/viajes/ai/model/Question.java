package com.viajes.ai.model;

import java.util.List;

public class Question {
    private String id;
    private String text;
    private String type; // text, select, multiselect, date
    private List<String> options;
    private boolean required = true;

    public Question() {}

    public Question(String id, String text, String type) {
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public Question(String id, String text, String type, List<String> options) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.options = options;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
}