package com;

public interface Observable {
    void Notify();
    void register(Observer o);
}
