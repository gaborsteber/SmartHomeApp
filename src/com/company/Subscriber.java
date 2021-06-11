package com.company;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Subscriber {
    private final String subscriber;
    private final String homeId;
    private String boilerType = "No boiler";
    private String airConditionerType = "No airconditioner";
    private List<Temperature> temperatures;

    public Subscriber(String _subscriber, String _homeId, String _boilerType, String _airConditionerType, List<Temperature> _temperatures)
    {
        this.subscriber = _subscriber;
        this.homeId = _homeId;
        this.boilerType = _boilerType;
        this.airConditionerType = _airConditionerType;
        this.temperatures = new ArrayList<Temperature>(_temperatures);
    }

    public void printSubscriber()
    {
        System.out.println("A feliratkozó neve: " + subscriber);
        System.out.println("Okosotthon azonosító: " + homeId);
        System.out.println("Kazán típusa: " + boilerType);
        System.out.println("Légkodicionáló típusa: " + airConditionerType);
        Iterator<Temperature> iterator = temperatures.iterator();

        while (iterator.hasNext()) {
           Temperature temp = new Temperature(iterator.next());
            System.out.println("Idő: " + temp.getPeriod() + " Hőmérséklet: " + temp.getTemperature());
        }
    }

    public String getSubscriber() {
        return subscriber;
    }

    public String getHomeId() {
        return homeId;
    }

    public String getBoilerType() {
        return boilerType;
    }

    public String getAirConditionerType() {
        return airConditionerType;
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setBoilerType(String boilerType) {
        this.boilerType = boilerType;
    }

    public void setAirConditionerType(String airConditionerType) {
        this.airConditionerType = airConditionerType;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }
}
