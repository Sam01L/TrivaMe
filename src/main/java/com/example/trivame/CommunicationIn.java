package com.example.trivame;

public class CommunicationIn implements Runnable {
    CommunicationConnection myConnection;
    boolean isServer;

    public CommunicationIn(CommunicationConnection connection, boolean isServer) {
        this.myConnection = connection;
        this.isServer = isServer;
    }

    @Override
    public void run() {
        boolean stayConnected = true;
        while (stayConnected && !Thread.currentThread().isInterrupted()) {
            TriviaMessage newMessage = null;
            try {
                newMessage = (TriviaMessage) myConnection.getInStream().readObject();
            } catch (Exception ex) {
                System.out.println("CommunicationIn lost connection: " + ex);
                break;
            }

            if (newMessage == null) continue;

            if (!isServer) {
                System.out.println("Client received: " + newMessage);
                continue;
            }

            if (newMessage.mode == 1) {
                myConnection.setName(newMessage.from);
                int playerCount = Server.allConnections.size();
                TriviaMessage welcome = new TriviaMessage(1, 6, "Players: " + playerCount, "SERVER", "ALL");
                boolean put = Server.theQueue.put(welcome);
                while (!put) put = Server.theQueue.put(welcome);

            } else if (newMessage.mode == 2 && "START".equals(newMessage.text)) {
                if (Server.allConnections.size() >= 2 && !Server.gameEngine.isGameStarted()) {
                    Server.gameEngine.startGame();
                    TriviaMessage question = Server.gameEngine.getNextQuestion();
                    boolean put = Server.theQueue.put(question);
                    while (!put) put = Server.theQueue.put(question);
                } else if (Server.allConnections.size() < 2) {
                    TriviaMessage wait = new TriviaMessage(1, 7, "Need 2+ players to start", "SERVER", newMessage.from);
                    boolean put = Server.theQueue.put(wait);
                    while (!put) put = Server.theQueue.put(wait);
                }

            } else if (newMessage.mode == 3) {
                boolean correct = Server.gameEngine.checkAnswer(newMessage.answerChoice);
                String result = correct ? "CORRECT" : "WRONG";
                TriviaMessage resultMsg = new TriviaMessage(1, 4, result, "SERVER", newMessage.from);
                boolean put = Server.theQueue.put(resultMsg);
                while (!put) put = Server.theQueue.put(resultMsg);

                if (Server.gameEngine.hasMoreQuestions()) {
                    try { Thread.sleep(3000); } catch (Exception e) {}
                    TriviaMessage next = Server.gameEngine.getNextQuestion();
                    boolean put2 = Server.theQueue.put(next);
                    while (!put2) put2 = Server.theQueue.put(next);
                } else {
                    TriviaMessage over = new TriviaMessage(1, 5, "GAME_OVER", "SERVER", "ALL");
                    boolean put2 = Server.theQueue.put(over);
                    while (!put2) put2 = Server.theQueue.put(over);
                }

            } else if (newMessage.mode == 9) {
                stayConnected = false;
            }
        }
        System.out.println("CommunicationIn done for: " + myConnection.getName());
    }
}