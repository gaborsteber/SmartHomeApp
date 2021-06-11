package com.company;

public interface iMonitor {

    default void getSession(String homeId) throws Exception {

    }
    default Session getMonitoredHome(){
        return null;
    }
}
