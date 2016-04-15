package com.garuda.core.events;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;

public class EventConsumer implements Runnable {
    private final VirtualMachine vm;
    private EventHandler handler;

    public EventConsumer(VirtualMachine vm, EventHandler handler) {
        this.vm = vm;
        this.handler = handler;
    }

    public void run() {
        while (true) {
            EventQueue queue = vm.eventQueue();
            try {
                EventSet eventSet = queue.remove();
                if (eventSet != null) {
                    for (Event event : eventSet) {
                        handler.handleEvent(event);
                    }
                    eventSet.resume();
                }
            } catch (VMDisconnectedException e) {
                handleDisconnectedException(e);
                break;
            } catch (InterruptedException e) {

            }
        }
    }

    private void handleDisconnectedException(VMDisconnectedException e) {
    }
}
