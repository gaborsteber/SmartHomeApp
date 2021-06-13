package com.company;
import java.util.List;

public class Main {

    public static void main(String[] args){
        List<Subscriber> subscribersList;
        iLoader subscribersToLoader = new Loader();


//        try {
//            GetDataFromServer.getDataFrom();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            GetDataFromServer.getDataFromWithUrl("KD34AF24DS");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            iMonitor getDataForMonitoring = new Monitor();
            getDataForMonitoring.getSession("KD34AF24DS");
            Session monitoredData = getDataForMonitoring.getMonitoredHome();
            System.out.println(monitoredData.getSessionId() + " - " + monitoredData.getTemperature() + " - " + monitoredData.isBoilerState() + " - " + monitoredData.isBoilerState());
        } catch (Exception e) {
            e.printStackTrace();
        }
//      Testing: data from server: 95FCR - 18 - true - true

        try {
            PostDataToServer.postDataTo();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
 //       TODO: implement POST method in Driver class
      try{
          iLoader subscribersFromFile = new Loader();
          subscribersList = subscribersFromFile.loadSubscribersToList("subscribers.json");
          for(int i=0; i<subscribersList.size(); i++) {
              subscribersList.get(i).printSubscriber();
          }
// Global Subscriber List print test - data valid!
      }
      catch (Exception e){
            e.printStackTrace();
      }

        try{
            subscribersToLoader.loadSubscribers("subscribers.json");
            subscribersList = subscribersToLoader.getSubscribers();

           for (int i=0; i<subscribersList.size();i++) {
                subscribersList.get(i).printSubscriber();
            }
 //Loader Class Subscriber List print test - data valid!

            List<Temperature> temps = subscribersList.get(0).getTemperatures(); //Getter test
            for (int i=0; i<temps.size();i++) {
                System.out.println(temps.get(i).getPeriod() + " - " + temps.get(i).getTemperature()); //Getter test print - data valid!
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

}