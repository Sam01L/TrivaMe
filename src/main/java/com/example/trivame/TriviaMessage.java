package com.example.trivame;

import java.io.Serial;
import java.io.Serializable;

public class TriviaMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;

    Integer version = 1;
    Integer mode;
    String text;
    String to;
    String from;
    String questionText;
    String[] options;
    String answerChoice;
    String correctAnswer;
    Integer roundNumber;
    Integer score;

    public TriviaMessage(Integer version, Integer mode, String text, String from, String to) {
        this.version = version;
        this.mode = mode;
        this.text = text;
        this.from = from;
        this.to = to;
    }

    public Integer getMode() { return mode; }
    public void setMode(Integer mode) { this.mode = mode; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    @Override
    public String toString() {
        return "TriviaMessage{mode=" + mode + ", text='" + text + "', to='" + to + "', from='" + from + "'}";
    }
}