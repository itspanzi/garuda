package com.garuda.core.events;

import com.sun.jdi.event.Event;

public interface EventHandler {
    void handleEvent(Event event);
}
