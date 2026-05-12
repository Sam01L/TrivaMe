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

            System.out.println("Server got message from " + myConnection.getName() + ": " + newMessage);

            if (newMessage.mode == 1) {
                myConnection.setName(newMessage.from);
                newMessage = new TriviaMessage(1, 2, "Welcome: " + newMessage.from, "SERVER", newMessage.from);
            } else if (newMessage.mode == 3) {
                newMessage = new TriviaMessage(1, 3, "Goodbye: " + newMessage.from, "SERVER", newMessage.from);
                stayConnected = false;
            }

            boolean putSuccess = Server.theQueue.put(newMessage);
            while (!putSuccess) {
                putSuccess = Server.theQueue.put(newMessage);
            }
        }

        System.out.println("CommunicationIn done for: " + myConnection.getName());
    }
}