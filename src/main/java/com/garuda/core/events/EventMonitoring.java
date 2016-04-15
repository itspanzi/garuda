package com.garuda.core.events;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.MethodEntryEvent;

import java.util.List;

public class EventMonitoring {

    public void consumeFor(final VirtualMachine vm) throws InterruptedException {
        new Thread(new EventConsumer(vm, new EventHandler() {
            public void handleEvent(Event event) {
                if (event instanceof MethodEntryEvent) {
                    EventMonitoring.this.handleMethodEntry((MethodEntryEvent) event);
                }
            }
        })).start();
    }

    private void handleMethodEntry(MethodEntryEvent event) {
        ThreadReference threadReference = event.thread();
        Method method = event.method();
        try {
            List<LocalVariable> arguments = method.arguments();
            for (LocalVariable argument : arguments) {
                threadReference.frames();
                StackFrame frame = threadReference.frame(0);
                Value value = frame.getValue(argument);
                System.out.println(String.format("arg: %s, value: %s ", argument.name(), value));
            }
        } catch (AbsentInformationException e) {
            System.out.println("Booo");
        } catch (IncompatibleThreadStateException e) {
            System.out.println("Booo");
        }
    }
}
