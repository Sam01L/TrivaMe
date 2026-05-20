package com.example.trivame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
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
    @FXML private Label timerCount;
    @FXML private Button startButton;
    @FXML private Button answerButton1;
    @FXML private Button answerButton2;
    @FXML private Button answerButton3;
    @FXML private Button answerButton4;
    @FXML private javafx.scene.shape.Rectangle correctRect;
    @FXML private javafx.scene.shape.Rectangle wrongRect;

    private ObjectOutputStream myObjOutput;
    private ObjectInputStream myObjInput;
    private String playerName = "Sam";
    private int currentQuestionNumber = 0;
    private Timeline timer;

    @FXML
    public void initialize() {
        playerNameLabel.setText(playerName);
        questionLabel.setVisible(false);
        questionCountLabel.setVisible(false);
        correctLabel.setVisible(false);
        wrongLabel.setVisible(false);
        timerCount.setVisible(false);
        setAnswerButtonsVisible(false);
        correctRect.setVisible(false);
        wrongRect.setVisible(false);
        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket ourSocket = new Socket("127.0.0.1", 12345);
            myObjOutput = new ObjectOutputStream(ourSocket.getOutputStream());
            myObjInput = new ObjectInputStream(ourSocket.getInputStream());

            TriviaMessage joinMsg = new TriviaMessage();
            joinMsg.setVersion(1);
            joinMsg.setMode(1);
            joinMsg.setText("Joined");
            joinMsg.setFrom(playerName);
            joinMsg.setTo("SERVER");
            myObjOutput.writeObject(joinMsg);
            myObjOutput.flush();

            Thread listenerThread = new Thread(this::listenForMessages);
            listenerThread.setDaemon(true);
            listenerThread.start();

        } catch (Exception ex) {
            System.out.println("Connection failed: " + ex);
        }
    }

    private void listenForMessages() {
        while (true) {
            try {
                TriviaMessage incoming = (TriviaMessage) myObjInput.readObject();

                if (incoming.mode == 2) {
                    Platform.runLater(() -> displayQuestion(incoming));
                } else if (incoming.mode == 4) {
                    boolean correct = "CORRECT".equalsIgnoreCase(incoming.text);
                    Platform.runLater(() -> showResult(correct));
                } else if (incoming.mode == 5) {
                    Platform.runLater(this::showGameOver);
                } else if (incoming.mode == 6) {
                    Platform.runLater(() -> questionLabel.setText(incoming.text));
                    Platform.runLater(() -> questionLabel.setVisible(true));
                } else if (incoming.mode == 7) {
                    Platform.runLater(() -> questionLabel.setText(incoming.text));
                    Platform.runLater(() -> questionLabel.setVisible(true));
                }

            } catch (Exception e) {
                break;
            }
        }
    }

    private void startTimer() {
        if (timer != null) timer.stop();
        final int[] seconds = {20};
        timerCount.setText("20");
        timerCount.setVisible(true);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds[0]--;
            timerCount.setText(String.valueOf(seconds[0]));
            if (seconds[0] <= 0) {
                timer.stop();
                setAnswerButtonsDisabled(true);
            }
        }));
        timer.setCycleCount(20);
        timer.play();
    }

    private void displayQuestion(TriviaMessage msg) {
        currentQuestionNumber++;
        questionLabel.setText(msg.questionText);
        questionLabel.setVisible(true);
        questionCountLabel.setText("Question " + currentQuestionNumber + " of 15");
        questionCountLabel.setVisible(true);

        if (msg.options != null) {
            answerButton1.setText(msg.options.get(0));
            answerButton2.setText(msg.options.get(1));
            answerButton3.setText(msg.options.get(2));
            answerButton4.setText(msg.options.get(3));
        }

        correctLabel.setVisible(false);
        wrongLabel.setVisible(false);
        setAnswerButtonsVisible(true);
        setAnswerButtonsDisabled(false);
        startTimer();
    }

    private void showResult(boolean correct) {
        if (timer != null) timer.stop();
        correctRect.setVisible(correct);
        correctLabel.setVisible(correct);
        wrongRect.setVisible(!correct);
        wrongLabel.setVisible(!correct);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            correctRect.setVisible(false);
            correctLabel.setVisible(false);
            wrongRect.setVisible(false);
            wrongLabel.setVisible(false);
        });
        pause.play();
    }

    private void showGameOver() {
        if (timer != null) timer.stop();
        questionLabel.setText("Game Over! Thanks for playing!");
        questionLabel.setVisible(true);
        timerCount.setVisible(false);
        setAnswerButtonsVisible(false);
        startButton.setVisible(false);
    }

    @FXML
    public void onStartClicked() {
        if (myObjOutput == null) {
            System.out.println("Not connected to server");
            return;
        }
        TriviaMessage startMsg = new TriviaMessage();
        startMsg.setVersion(1);
        startMsg.setMode(2);
        startMsg.setText("START");
        startMsg.setFrom(playerName);
        startMsg.setTo("SERVER");
        sendMessage(startMsg);
        startButton.setDisable(true);
    }

    @FXML public void onAnswer1() { submitAnswer(1); }
    @FXML public void onAnswer2() { submitAnswer(2); }
    @FXML public void onAnswer3() { submitAnswer(3); }
    @FXML public void onAnswer4() { submitAnswer(4); }

    private void submitAnswer(Integer choice) {
        if (timer != null) timer.stop();
        setAnswerButtonsDisabled(true);

        TriviaMessage answerMsg = new TriviaMessage();
        answerMsg.setVersion(1);
        answerMsg.setMode(3);
        answerMsg.setAnswerChoice(choice);
        answerMsg.setRoundNumber(currentQuestionNumber);
        answerMsg.setFrom(playerName);
        answerMsg.setTo("SERVER");

        sendMessage(answerMsg);
    }

    private void sendMessage(TriviaMessage msg) {
        try {
            myObjOutput.writeObject(msg);
            myObjOutput.flush();
        } catch (Exception e) {
            System.out.println("Send failed: " + e);
        }
    }

    private void setAnswerButtonsVisible(boolean visible) {
        answerButton1.setVisible(visible);
        answerButton2.setVisible(visible);
        answerButton3.setVisible(visible);
        answerButton4.setVisible(visible);
    }

    private void setAnswerButtonsDisabled(boolean disabled) {
        answerButton1.setDisable(disabled);
        answerButton2.setDisable(disabled);
        answerButton3.setDisable(disabled);
        answerButton4.setDisable(disabled);
    }
}