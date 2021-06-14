package com.company;

import java.util.List;
import java.util.Map;

public interface iDriver {

    int sendCommand(Subscriber subs, boolean boiler, boolean ac) throws Exception;
    Map<String, List<String>> getCommands();
    //void commandSend(List<Subscriber> subscribersList) throws Exception;
}
