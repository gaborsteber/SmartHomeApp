package com.company;

import java.util.List;

public interface iDriver {

    public int sendCommand(Subscriber subs, boolean boiler, boolean ac) throws Exception;
    public void commandSend(List<Subscriber> subscribersList) throws Exception;
}
