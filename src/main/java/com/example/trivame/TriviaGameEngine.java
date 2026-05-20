package com.example.trivame;

import java.util.ArrayList;

public class TriviaGameEngine {

    private int currentRound = 0;
    private boolean gameStarted = false;

    private String[][] questions = {
            {"What is the capital of France?", "Paris", "London", "Rome", "Berlin", "Paris"},
            {"What is 5 x 6?", "30", "25", "35", "40", "30"},
            {"What color is the sky?", "Blue", "Green", "Red", "Yellow", "Blue"},
            {"How many sides does a triangle have?", "3", "4", "5", "6", "3"},
            {"What is the largest planet?", "Jupiter", "Saturn", "Earth", "Mars", "Jupiter"},
            {"What is 10 + 15?", "25", "20", "30", "35", "25"},
            {"Who wrote Romeo and Juliet?", "Shakespeare", "Dickens", "Twain", "Poe", "Shakespeare"},
            {"What is the fastest land animal?", "Cheetah", "Lion", "Horse", "Tiger", "Cheetah"},
            {"How many days in a week?", "7", "5", "6", "8", "7"},
            {"What is H2O?", "Water", "Oxygen", "Hydrogen", "Carbon", "Water"},
            {"What continent is Egypt in?", "Africa", "Asia", "Europe", "America", "Africa"},
            {"How many months in a year?", "12", "10", "11", "13", "12"},
            {"What is the smallest prime number?", "2", "1", "3", "5", "2"},
            {"What language do Brazilians speak?", "Portuguese", "Spanish", "English", "French", "Portuguese"},
            {"What is the square root of 64?", "8", "6", "7", "9", "8"}
    };

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startGame() {
        gameStarted = true;
        currentRound = 0;
    }

    public boolean hasMoreQuestions() {
        return currentRound < questions.length;
    }

    public TriviaMessage getNextQuestion() {
        String[] q = questions[currentRound];
        TriviaMessage msg = new TriviaMessage();
        msg.setMode(2);
        msg.questionText = q[0];
        ArrayList<String> options = new ArrayList();
        options.add(q[1]);
        options.add(q[2]);
        options.add(q[3]);
        options.add(q[4]);
        msg.setOptions(options);
        msg.setCorrectAnswer(Integer.parseInt(q[5]));
        msg.setRoundNumber(currentRound + 1);
        currentRound++;
        return msg;
    }

    public boolean checkAnswer(String answer) {
        String correct = questions[currentRound - 1][5];
        return correct.equalsIgnoreCase(answer);
    }
}