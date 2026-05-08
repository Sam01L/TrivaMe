package com.example.trivame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to server running on SAME 127.0.0.1 computer
            Socket ourSocket = new Socket("127.0.0.1", 12345);

            // Client MUST create InputStream BEFORE OutputStream!!!!!!
            ObjectOutputStream myObjOutput = new ObjectOutputStream(ourSocket.getOutputStream());
            ObjectInputStream myObjInput = new ObjectInputStream(ourSocket.getInputStream());
            CommunicationConnection newConnection = new CommunicationConnection("Player",ourSocket,myObjInput,myObjOutput);
            CommunicationIn myCommunicationIn = new CommunicationIn(newConnection, false);
            Thread communicationInThread = new Thread(myCommunicationIn);
            communicationInThread.start();

            TriviaMessage message1 = new TriviaMessage(1,1,"Joined","Player", "SERVER");
            myObjOutput.writeObject(message1);
            myObjOutput.flush();

            Scanner inputTextScanner = new Scanner(System.in);
            boolean keepScanning = true;
            while (keepScanning) {
                System.out.print("Type your message: ");
                String theText = inputTextScanner.nextLine();
                if (theText.equalsIgnoreCase("STOP")) {
                    keepScanning = false;
                } else {
                    TriviaMessage newMessage = new TriviaMessage(1, 2, theText, "Mr. H", "ALL");
                    myObjOutput.writeObject(newMessage);
                    myObjOutput.flush();
                }
            }

            TriviaMessage message3 = new TriviaMessage(1,3,"","Mr. H", "SERVER");
            myObjOutput.writeObject(message3);
            myObjOutput.flush();
            communicationInThread.interrupt();
        } catch (Exception ex) {
            System.out.println("Socket failed: " + ex);
        }
        System.out.println("Client.main DONE");
    }
}
