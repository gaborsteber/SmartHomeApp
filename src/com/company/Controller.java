package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    /*Az alabbi harom marad 'public' elerhetoseggel, mert igy konnyebb lesz majd kezelni. Mivel
    leglabb ket hazra van internetes elres, ezert listat alkalmazunk, ahova legalabb ketto hazra
    elhelyezhetoek az adatok. Megfelelo sorrendben kell oket elhelyezni a listakban.*/

    List<Monitor> monitors = new ArrayList<>();
    List<Driver> drivers = new ArrayList<>();
    List<Subscriber> subscribers = new ArrayList<>();

    //constructor mindharom adattaggal

    public Controller(List<Monitor> monitors, List<Driver> drivers, List<Subscriber> subscribers) {
        this.monitors = monitors;
        this.drivers = drivers;
        this.subscribers = subscribers;
    }

    //settere es getterek kovetkeznek, ha kesobb kellenenek
    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Subscriber> getLoaders() {
        return subscribers;
    }

    public void setSubscriber(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void addMonitor(Monitor m) {
        monitors.add(m);
    }

    public void addDriver(Driver d) {
        drivers.add(d);
    }

    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }

    public void controlCheckService(List<Subscriber> subscribersList) throws Exception { //controllCheckService
        for (int i = 0; i < subscribersList.size(); i++) {
            iMonitor monitoredHome = new Monitor();
            iDriver sendToHome = new Driver();
            Session actualHome;
            monitoredHome.getSession(subscribersList.get(i).getHomeId());
            actualHome = monitoredHome.getMonitoredHome();
            int statusFromServer = 0;
            System.out.println("Kívánt hőmérséklet: " + Subscriber.getTemperatureForNow(subscribersList.get(i)) + " - Lekérdezett hőmérséklet: " + actualHome.getTemperature());
            if (Subscriber.getTemperatureForNow(subscribersList.get(i)) >= actualHome.getTemperature())  //A kértnél alacsonyabb a hőmérésklet, fűtés szükséges!
            {
                System.out.println("Fűtés szükséges!");
                statusFromServer = sendToHome.sendCommand(subscribersList.get(i), true, false);
            } // IF hőmérséklet összehasonlítása
            else //Magasabb a hőmérséklet a kértnél, hűtés szükséges!
            {
                System.out.println("Hűtés szükséges!");
                statusFromServer = sendToHome.sendCommand(subscribersList.get(i), false, true);
            }
                //System.out.println(response.toString());
                if (statusFromServer == 100) {
                    System.out.println("A szerver visszaigazolta a sikeres beállítást!");
                }
                if (statusFromServer == 101) {
                    System.out.println("A szerver hibás parancs választ adott!");
                }
            }
        }
    }

    //void sendCommand()


