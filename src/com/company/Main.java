package com.company;
import java.util.List;

public class Main {

    public static void main(String[] args){
        List<Subscriber> subscribersList = null;
        List<Driver> driversList = null;
        List<Monitor> monitorsList = null;
        iLoader subscribersToLoader = new Loader();
        iMonitor getDataForMonitoring = new Monitor();
//        iDriver commandService = new Driver();
        Controller commandService = new Controller(monitorsList, driversList, subscribersList);

//        try {
//            GetDataFromServer.getDataFrom();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            GetDataFromServer.getDataFromWithUrl("KD34AF24DS");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
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
//        try {
//            PostDataToServer.postDataTo();
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }

      try{
          iLoader subscribersFromFile = new Loader();
          subscribersList = subscribersFromFile.loadSubscribersToList("subscribers.json");
//          for(int i=0; i<subscribersList.size(); i++) {
//              subscribersList.get(i).printSubscriber();
//          }
// Global Subscriber List print test - data valid!
      }
      catch (Exception e){
            e.printStackTrace();
      }
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
        try {
            commandService.controlCheckService(subscribersList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

   }

}