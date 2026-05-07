package com.example.trivame;
//Written by Alex Chancey

public class AlexQueue {
    int currentIndex = 0;
    int getIndex = 0;
    int objsInQueue = 0;
    TriviaMessage[] objects = new TriviaMessage[100];

    public synchronized boolean put(TriviaMessage obj) {
        if(objsInQueue<100) {
            objects[currentIndex] = obj;
            currentIndex++;
            objsInQueue++;
            if(currentIndex==100){
                currentIndex=0;
            }
            return true;
        }
        return false;
    }

    public synchronized TriviaMessage get() {
        if(objsInQueue>0) {
            TriviaMessage object = objects[getIndex];
            getIndex++;
            objsInQueue--;
            if(getIndex==100){
                getIndex=0;
            }

            return object;
        }
        return null;
    }
}