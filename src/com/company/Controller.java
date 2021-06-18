package com.company;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Controller {

    /*Az alabbi harom marad 'public' elerhetoseggel, mert igy konnyebb lesz majd kezelni. Mivel
    leglabb ket hazra van internetes elres, ezert listat alkalmazunk, ahova legalabb ketto hazra
    elhelyezhetoek az adatok. Megfelelo sorrendben kell oket elhelyezni a listakban.*/

    List<Monitor> monitors;
    List<Driver> drivers;
    List<Subscriber> subscribers;

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

    //A logolási metódus később került ide.
    private void logFailure(String homeID, String subscriber) {
        //hiba logolása fileba
        try {
            File logFile = new File("error_log_file.txt");
            if (logFile.createNewFile()) {
                System.out.println("Elkészítettem a hibák logolására való fájlt: " + logFile.getName() + " és ide írok...");
            } else {
                System.out.println("A már létező log file-ba írok...");
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a log file létrehozása során...");
            e.printStackTrace();
        }
        try {
            FileWriter logWriter = new FileWriter("error_log_file.txt");
            logWriter.write("Hibát rögzítettem a " + homeID + " azonosító számú házban, amelynek tulajdonosa " + subscriber +".");
            logWriter.close();
        } catch (Exception e) {
            System.out.println("Hiba történt a fájl kiírása során:");
            e.printStackTrace();
        }
    }

    public void controlCheckService(List<Subscriber> subscribersList) throws Exception { //controllCheckService
        for (int i = 0; i < subscribersList.size(); i++) {
            iMonitor homeMonitor = new Monitor();
            iDriver homeDriver = new Driver();
            Session actualHome;
            homeMonitor.getSession(subscribersList.get(i).getHomeId());
            actualHome = homeMonitor.getMonitoredHome();
            int statusFromServer = 0;
            System.out.println("Kívánt hőmérséklet: " + Subscriber.getTemperatureForNow(subscribersList.get(i)) + " - Lekérdezett hőmérséklet: " + actualHome.getTemperature());

            double elvartHomerseklet = Subscriber.getTemperatureForNow(subscribersList.get(i));
            double aktualisHomerseklet = actualHome.getTemperature();
            double elteres = (elvartHomerseklet - aktualisHomerseklet);
            Math.abs(elteres);

            //TODO: itt biztosan jó a >=, nem fordítva kellene írni?
            //TODO: szerintem a kód biztosan megfelel a feladat 3.4 pontjában kért megvalósításnak? Ezt beszéljük meg telefonon
            //
            if (Subscriber.getTemperatureForNow(subscribersList.get(i)) >= actualHome.getTemperature())  //A kértnél alacsonyabb a hőmérésklet, fűtés szükséges!
            {
                //Ha a kivant legalább 20%-al eltér a jelenlegitől, akkor logolni kell a hibat egy fileba
                if (elteres / (elvartHomerseklet / 100) > 20) {
                    System.out.println("Feltételezhetően hibát észleletem, logolom!");
                    logFailure(subscribersList.get(i).getHomeId(), subscribersList.get(i).getSubscriber());
                }
                System.out.println("Fűtés szükséges!");
                statusFromServer = homeDriver.sendCommand(subscribersList.get(i), true, false);
            } // IF hőmérséklet összehasonlítása
            else //Magasabb a hőmérséklet a kértnél, hűtés szükséges!
            {
                if (elteres / (elvartHomerseklet / 100) > 20) {
                    logFailure(subscribersList.get(i).getHomeId(), subscribersList.get(i).getSubscriber());
                    System.out.println("Feltételezhetően hibát észleletem, logolom!");
                }
                System.out.println("Hűtés szükséges!");
                statusFromServer = homeDriver.sendCommand(subscribersList.get(i), false, true);
            }
            //System.out.println(response.toString());
            if (statusFromServer == 100) {
                System.out.println("A szerver visszaigazolta a sikeres beállítást!");
            }
            if (statusFromServer == 101 || statusFromServer == 102) {
                System.out.println("A szerver hibás parancs választ adott!");

            }
        }
    }
}

//void sendCommand()


