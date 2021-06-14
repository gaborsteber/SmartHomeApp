package com.company;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

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

    public int getBoilerTypeInt()
    {
        int boilerType = 0;
        if(this.getBoilerType().equals("Boiler 1200W"))
        {
            boilerType = 1;
        }
        else if (this.getBoilerType().equals("Boiler p5600"))
        {
            boilerType = 2;
        }
        return  boilerType;
    }
    public int getAirTypeInt()
    {
        int airType = 0;
        if(this.getAirConditionerType().equals("Air p5600"))
        {
            airType = 1;
        }
        else if (this.getAirConditionerType().equals("Air c320"))
        {
            airType = 2;
        }
        return  airType;
    }
    public static double getTemperatureForNow(Subscriber subscriber) throws ParseException {
        int temperatureNow = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date date = new Date();
        LocalTime time = LocalTime.now();
        int index = 0;
        for(int i=0; i<subscriber.getTemperatures().size(); i++)
        {
            String startOfPeriod = subscriber.getTemperatures().get(i).getPeriod().substring(0,2) + ":00";
            String endOfPeriod;
            int endOfHour = parseInt(subscriber.getTemperatures().get(i).getPeriod().substring(3,5))-1;
            String hourBackToString = Integer.toString(endOfHour);
            if(hourBackToString.length() == 1)
            {
                endOfPeriod = "0" + hourBackToString +":59";
            }
            else endOfPeriod = hourBackToString + ":59";

            LocalTime startP = LocalTime.parse(startOfPeriod);
            LocalTime endP = LocalTime.parse(endOfPeriod);
            //System.out.println(startP + " - " + endP);
            if(time.isAfter(startP) && time.isBefore(endP))
            {
                index = i;
            }
        }
        //System.out.println(subscriber.getTemperatures().get(index).getTemperature());
        return subscriber.getTemperatures().get(index).getTemperature();
    }
}
