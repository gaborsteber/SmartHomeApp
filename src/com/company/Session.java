package com.company;

public class Session {
    private String sessionId;
    private double temperature;
    private boolean boilerState;
    private boolean airConditionerState;

    public Session(String _sessionId, double _temperature, boolean _boilerState, boolean _airConditionerState) {
        this.sessionId = _sessionId;
        this.temperature = _temperature;
        this.boilerState = _boilerState;
        this.airConditionerState = _airConditionerState;
    }

    public String getSessionId() {
        return sessionId;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isBoilerState() {
        return boilerState;
    }

    public boolean isAirConditionerState() {
        return airConditionerState;
    }
}
