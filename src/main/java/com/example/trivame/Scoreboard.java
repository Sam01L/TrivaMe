package com.example.trivame;

import java.util.ArrayList;

public class Scoreboard {
    public ArrayList<Integer> theScores;

    public Scoreboard() {
        this.theScores = new ArrayList<>();
    }

    public ArrayList<Integer> getTheScores() {
        return theScores;
    }

    public void setTheScores(ArrayList<Integer> theScores) {
        this.theScores = theScores;
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
                "theScores=" + theScores +
                '}';
    }
}

