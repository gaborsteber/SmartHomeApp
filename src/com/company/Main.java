package com.company;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args){
        List<Subscriber> subscribersList = null;
        int millisInAMinute = 30000; //5 perchez 60000*5 re kell allitani
        long time = System.currentTimeMillis();
        int chooseFromMenu = 0;
        Scanner input = new Scanner(System.in);

        try{
            iLoader subscribersFromFile = new Loader();
            subscribersList = subscribersFromFile.loadSubscribersToList("subscribers.json");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Controller commandService = new Controller(subscribersList);

        System.out.println("MENU:");
        System.out.println("1. Végrehajtás egyszer");
        System.out.println("2. Időzített végrehajtás");
        chooseFromMenu = input.nextInt();

        switch (chooseFromMenu) {
            case 1:
                try {
                    commandService.controlCheckService();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case 2:
                Runnable update = new Runnable() {
                    public void run() {
                        System.out.println("...........run again...........");
                        try {
                            commandService.controlCheckService();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        update.run();
                    }
                }, time % millisInAMinute, millisInAMinute);

                update.run();
                break;
            }
    }

}

//        List<Driver> driversList = null;
//        List<Monitor> monitorsList = null;
//        iLoader subscribersToLoader = new Loader();
//        iMonitor getDataForMonitoring = new Monitor();
//        iDriver commandService = new Driver();

//            try{
//                iLoader subscribersFromFile = new Loader();
//                subscribersList = subscribersFromFile.loadSubscribersToList("subscribers.json");
////          for(int i=0; i<subscribersList.size(); i++) {
////              subscribersList.get(i).printSubscriber();
////          }
//// Global Subscriber List print test - data valid!
//            }
//            catch (Exception e){
//                e.printStackTrace();

//        try {
//            iMonitor getDataForMonitoring = new Monitor();
//            getDataForMonitoring.getSession("KD34AF24DS");
//            Session monitoredData = getDataForMonitoring.getMonitoredHome();
//            System.out.println(monitoredData.getSessionId() + " - " + monitoredData.getTemperature() + " - " + monitoredData.isBoilerState() + " - " + monitoredData.isBoilerState());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////      Testing: data from server: 95FCR - 18 - true - true
//
//
//        try{
//            subscribersToLoader.loadSubscribers("subscribers.json");
//            subscribersList = subscribersToLoader.getSubscribers();
//
//           for (int i=0; i<subscribersList.size();i++) {
//                subscribersList.get(i).printSubscriber();
//            }
// //Loader Class Subscriber List print test - data valid!
//
//            List<Temperature> temps = subscribersList.get(0).getTemperatures(); //Getter test
//            for (int i=0; i<temps.size();i++) {
//                System.out.println(temps.get(i).getPeriod() + " - " + temps.get(i).getTemperature()); //Getter test print - data valid!
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//
//        Subscriber _subscriber = subscribersList.get(1);
//        try {
//            System.out.println(Subscriber.getTemperatureForNow(_subscriber));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

//        try {
//            commandService.commandSend(subscribersList);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        // commandService method kész, megkapja a feliratkozók listáját, majd egyesével lekérdezi az okosotthonok
        // aktuális állapotát, végül elvégzi a szükséges műveletet.

        //Driver test

//        iDriver dd = new Driver();
//
//        int myInt = dd.sendCommand(subscribersList.get(1), true, true);
//        if (myInt == 100) {
//              //eredmény kiértékelése
//        }

//      try {
//            commandService.controlCheckService();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

