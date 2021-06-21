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

    List<Subscriber> subscribers;

    //constructor mindharom adattaggal

    public Controller(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Subscriber> getLoaders() {
        return subscribers;
    }

    public void setSubscriber(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }

    //A logolási metódus később került ide
    private void logFailure(String homeID, String subscriberID) {
        //hiba logolása fileba
        try {
            File logFile = new File("error_log_file.txt");
            if (logFile.createNewFile()) {
                System.out.println("Elkészítettem a hibák logolására való fájlt: " + logFile.getName() + " és ide írok..." + "\n");
            } else {
                System.out.println("A már létező log file-ba írok...");
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a log file létrehozása során...");
            e.printStackTrace();
        }
        try {
            FileWriter logWriter = new FileWriter("error_log_file.txt", true);
            logWriter.append("Hibát rögzítettem a " + homeID + " azonosító számú házban, amelynek tulajdonosa " + subscriberID + "." + "\n");
            logWriter.close();
        } catch (Exception e) {
            System.out.println("Hiba történt a fájl kiírása során:");
            e.printStackTrace();
        }
    }

    public void controlCheckServiceWithParameter(List<Subscriber> subscribersList) throws Exception { //controllCheckService
        double elvartHomerseklet;
        double aktualisHomerseklet;
        double elteres;
        int statusFromServer = 0;
        for (int i = 0; i < subscribersList.size(); i++) {
            iMonitor homeMonitor = new Monitor();
            iDriver homeDriver = new Driver();
            Session actualHome;
            homeMonitor.getSession(subscribersList.get(i).getHomeId());
            actualHome = homeMonitor.getMonitoredHome();
            System.out.println("Kívánt hőmérséklet: " + Subscriber.getTemperatureForNow(subscribersList.get(i)) + " - Lekérdezett hőmérséklet: " + actualHome.getTemperature());

            elvartHomerseklet = Subscriber.getTemperatureForNow(subscribersList.get(i));
            aktualisHomerseklet = actualHome.getTemperature();
            elteres = (elvartHomerseklet - aktualisHomerseklet);
            if (elteres < 0)
                elteres = elteres * -1;

            if (Subscriber.getTemperatureForNow(subscribersList.get(i)) > actualHome.getTemperature())  //A kértnél alacsonyabb a hőmérésklet, fűtés szükséges!
            {
                //Ha a kivant legalább 20%-al eltér a jelenlegitől, akkor logolni kell a hibat egy fileba
                if (elteres / (elvartHomerseklet / 100) > 20) {
                    System.out.println("Feltételezhetően hibát észleletem, logolom!");
                    logFailure(subscribersList.get(i).getHomeId(), subscribersList.get(i).getSubscriber());
                }
                System.out.println("Fűtés szükséges!");
                statusFromServer = homeDriver.sendCommand(subscribersList.get(i), true, false);
            } // IF hőmérséklet összehasonlítása
            else if (Subscriber.getTemperatureForNow(subscribersList.get(i)) < actualHome.getTemperature())//Magasabb a hőmérséklet a kértnél, hűtés szükséges!
            {
                if (elteres / (elvartHomerseklet / 100) > 20) {
                    logFailure(subscribersList.get(i).getHomeId(), subscribersList.get(i).getSubscriber());
                    System.out.println("Feltételezhetően hibát észleletem, logolom!");
                    logFailure(subscribersList.get(i).getHomeId(), subscribersList.get(i).getSubscriber());
                }
                System.out.println("Hűtés szükséges!");
                statusFromServer = homeDriver.sendCommand(subscribersList.get(i), false, true);
            } else {
                statusFromServer = homeDriver.sendCommand(subscribersList.get(i), false, false);
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

    public void controlCheckService() throws Exception { //controllCheckService
        double elvartHomerseklet;
        double aktualisHomerseklet;
        double elteres;
        int statusFromServer = 0;
        for (int i = 0; i < subscribers.size(); i++) {
            iMonitor homeMonitor = new Monitor();
            iDriver homeDriver = new Driver();
            Session actualHome;
            homeMonitor.getSession(subscribers.get(i).getHomeId());
            actualHome = homeMonitor.getMonitoredHome();
            System.out.println("Kívánt hőmérséklet: " + Subscriber.getTemperatureForNow(subscribers.get(i)) + " - Lekérdezett hőmérséklet: " + actualHome.getTemperature());

            elvartHomerseklet = Subscriber.getTemperatureForNow(subscribers.get(i));
            aktualisHomerseklet = actualHome.getTemperature();
            elteres = (elvartHomerseklet - aktualisHomerseklet);
            if (elteres < 0)
                elteres = elteres * -1;

            if (Subscriber.getTemperatureForNow(subscribers.get(i)) > actualHome.getTemperature())  //A kértnél alacsonyabb a hőmérésklet, fűtés szükséges!
            {
                //Ha a kivant legalább 20%-al eltér a jelenlegitől, akkor logolni kell a hibat egy fileba
                if (elteres / (aktualisHomerseklet / 100) >= 20) {
                    System.out.println("Feltételezhetően hibát észleletem, logolom!");
                    logFailure(subscribers.get(i).getHomeId(), subscribers.get(i).getSubscriber());
                }
                System.out.println("Fűtés szükséges!");
                statusFromServer = homeDriver.sendCommand(subscribers.get(i), true, false);
            } // IF hőmérséklet összehasonlítása
            else if (Subscriber.getTemperatureForNow(subscribers.get(i)) < actualHome.getTemperature())//Magasabb a hőmérséklet a kértnél, hűtés szükséges!
            {
                if (elteres / (aktualisHomerseklet / 100) >= 20) {
                    logFailure(subscribers.get(i).getHomeId(), subscribers.get(i).getSubscriber());
                    System.out.println("Feltételezhetően hibát észleletem, logolom!");
                    logFailure(subscribers.get(i).getHomeId(), subscribers.get(i).getSubscriber());
                }
                System.out.println("Hűtés szükséges!");
                statusFromServer = homeDriver.sendCommand(subscribers.get(i), false, true);
            } else {
                statusFromServer = homeDriver.sendCommand(subscribers.get(i), false, false);
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


