package com.example.trivame;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class TriviaMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;

    Integer version = 1;
    Integer mode;
    String text;
    String to;
    String from;
    String questionText;
    ArrayList<String> options ;
    Integer answerChoice;
    Integer correctAnswer;
    Integer roundNumber;
    Scoreboard score = new Scoreboard();

    public TriviaMessage() {
        this.version = 1;
        this.mode = -1;
        this.text = "";
        this.from = "SERVER";
        this.to = "ALL";
        this.questionText = "";
        this.options = new ArrayList<String>();
        this.options.add("1");
        this.options.add("2");
        this.options.add("3");
        this.options.add("4");
        this.answerChoice = -1;
        this.correctAnswer = -1;
        this.roundNumber  = 0;
        this.score = new Scoreboard();;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public Integer getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(Integer answerChoice) {
        this.answerChoice = answerChoice;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Scoreboard getScore() {
        return score;
    }

    public void setScore(Scoreboard score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TriviaMessage{" +
                //"version=" + version +
                ", mode=" + mode +
                ", text='" + text + '\'' +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", questionText='" + questionText + '\'' +
                ", options=" + options +
                ", answerChoice=" + answerChoice +
                ", correctAnswer=" + correctAnswer +
                ", roundNumber=" + roundNumber +
                ", score=" + score +
                '}';
    }
}