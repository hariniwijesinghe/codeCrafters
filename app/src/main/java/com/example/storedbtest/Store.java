package com.example.storedbtest;

public class Store {
    private String name;
    private double rating;
    private String phone;
    private String address;
    private String workingHours;
    private String url;

    public Store() {
        // Default constructor required for calls to DataSnapshot.getValue(Store.class)
    }

    public Store(String name, double rating, String phone, String address, String workingHours, String url) {
        this.name = name;
        this.rating = rating;
        this.phone = phone;
        this.address = address;
        this.workingHours = workingHours;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public String getUrl() {
        return url;
    }
}
