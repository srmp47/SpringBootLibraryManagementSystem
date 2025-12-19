package com.example.library.print.observers.listeners;

import com.example.library.models.enums.EventType;

public class PrinterListener implements EventListener {
    @Override
    public void update(EventType eventType, String message) {
        System.out.println("📢 Event: " + eventType + " | Message: " + message);
    }
}