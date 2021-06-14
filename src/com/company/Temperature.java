package com.company;

public class Temperature {
    private String period;
    private double temperature;

    Temperature(String _period,  double _temperature)
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

    public double getTemperature() {
        return temperature;
    }

}
