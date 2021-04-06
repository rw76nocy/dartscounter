package com.example.testapp;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int points;
    private int startpoints;
    private int darts;
    private int round;
    private double average;
    private List<Integer> history = new ArrayList<>();

    public Player(String name, int points){
        this.name = name;
        this.points = points;
        this.startpoints = points;
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
        //Average mit 3 Darts
        if(this.history.size()>0){
            int round = (this.history.size()/3);
            int darts = this.history.size()%3;
            if(darts == 0){
                int i = (int)(((double)(startpoints-points)/round)*10);
                this.average = ((double)(i))/10;
                return this.average;
            }
            return this.average;
        }
        return 0.0;

        /*
        //Average pro Dart
        if(this.history.size()>0){
            this.average = (double)(startpoints-points)/(this.history.size());
            int i = (int) (average*10);
            this.average = (double) (i/10);
            return average;
        }
        return 0.0;*/
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
