package com.garuda.core;

import com.garuda.core.config.Config;
import com.garuda.core.events.EventMonitoring;
import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Pavan Sudarshan
 */
public class Startup {

    private boolean connected = true;

    public void startup() throws Exception {
        AttachingConnector connector = findLaunchingConnector();
        Config config = new Config();
        VirtualMachine vm = connector.attach(attarchArguments(connector, config));

        EventRequestManager eventRequestManager = vm.eventRequestManager();
        MethodEntryRequest request = eventRequestManager.createMethodEntryRequest();
        request.addClassFilter(config.getClassFilter());
        request.enable();

        new EventMonitoring().consumeFor(vm);

    }

    private Map<String, Connector.Argument> attarchArguments(AttachingConnector connector, Config config) {
        Map<String, Connector.Argument> arguments = connector.defaultArguments();

        Connector.Argument hostname = arguments.get("hostname");
        hostname.setValue(config.hostname());

        Connector.Argument port = arguments.get("port");
        port.setValue(config.port());

        Connector.Argument timeout = arguments.get("timeout");
        timeout.setValue(config.timeout());

        return arguments;
    }

    private AttachingConnector findLaunchingConnector() {
        List<Connector> connectors = Bootstrap.virtualMachineManager().allConnectors();
        for (Connector connector : connectors) {
            if (connector.name().equals("com.sun.jdi.SocketAttach")) {
                return (AttachingConnector) connector;
            }
        }
        throw new Error("No launching connector");
    }

    public static void main(String[] args) throws Exception {
        new Startup().startup();
    }

}
