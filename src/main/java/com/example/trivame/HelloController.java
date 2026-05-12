package com.example.trivame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HelloController {

    @FXML private Label playerNameLabel;
    @FXML private Label questionLabel;
    @FXML private Label questionCountLabel;
    @FXML private Label correctLabel;
    @FXML private Label wrongLabel;
    @FXML private Button startButton;
    @FXML private Button answerButton1;
    @FXML private Button answerButton2;
    @FXML private Button answerButton3;
    @FXML private Button answerButton4;

    private ObjectOutputStream myObjOutput;
    private ObjectInputStream myObjInput;
    private String playerName = "Sam";
    private int currentQuestionNumber = 0;

    @FXML
    public void initialize() {
        playerNameLabel.setText(playerName);
        questionLabel.setVisible(false);
        questionCountLabel.setVisible(false);
        correctLabel.setVisible(false);
        wrongLabel.setVisible(false);
        setAnswerButtonsVisible(false);
        connectToServer();
    }

