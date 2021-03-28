package com.example.testapp;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int points;
    private int darts;
    private int round;
    private double average;
    private List<Integer> history = new ArrayList<>();

    public Player(String name, int points){
        this.name = name;
        this.points = points;
        this.darts = 0;
        this.round = 1;
        this.average = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDarts() {
        return darts;
    }

    public void setDarts(int darts) {
        this.darts = darts;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void setHistory(List<Integer> history) {
        this.history = history;
    }
}
