package com.example.onlinenotesmanager;

public class Notes {
    public String title;
    public String description;
    public String date;
    public long timestamp;


    public Notes(String title, String date, String description,long timestamp) {
        this.title = title;
        this.date = date;
        this.description=description;
        this.timestamp=timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
