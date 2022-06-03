package com.example.monitorizareangajati2.Observer;

public interface Observable{
    void notifyObservers(UpdateType type);
    void addObserver(Observer obs);
}
