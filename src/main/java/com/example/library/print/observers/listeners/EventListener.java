package com.example.library.print.observers.listeners;

import com.example.library.models.enums.EventType;

public interface EventListener {
    void update(EventType eventType, String message);
}