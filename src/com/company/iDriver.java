package com.company;

import java.util.List;

public interface iDriver {

    int sendCommand(Subscriber subs, boolean boiler, boolean ac) throws Exception;

    void commandSend(List<Subscriber> subscribersList) throws Exception;
}
