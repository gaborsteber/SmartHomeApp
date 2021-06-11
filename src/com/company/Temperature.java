package com.company;

public class Temperature {
    private String period;
    private int temperature;

    Temperature(String _period,  int _temperature)
    {
        period = _period;
        temperature = _temperature;
    }

    Temperature(Temperature temp)
    {
        period = temp.period;
        temperature = temp.temperature;
    }

    public String getPeriod() {
        return period;
    }

    public int getTemperature() {
        return temperature;
    }
}
