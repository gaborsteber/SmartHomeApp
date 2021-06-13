package com.company;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.*;


public class Loader implements iLoader{
    private List<Subscriber> subscribers;

    @Override
    public List<Subscriber> getSubscribers(){ return subscribers; }

    @Override
    public void loadSubscribers(String fajlNev) throws Exception {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(fajlNev));
            List<Subscriber> subscriberList = new ArrayList<Subscriber>();
            List<Temperature> subscriberTemp = new ArrayList<Temperature>();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
            JSONArray subscribers = (JSONArray) jsonObject.get("subscribers");
            Iterator<org.json.simple.JSONObject> iterator = subscribers.iterator();

            while (iterator.hasNext()) {
                org.json.simple.JSONObject valasz = new org.json.simple.JSONObject(iterator.next());
                org.json.JSONObject myResponse = new org.json.JSONObject(valasz.toString());
                JSONArray newArray = (JSONArray) valasz.get("temperatures");
                Iterator<org.json.simple.JSONObject> iterate = newArray.iterator();

                while(iterate.hasNext()) {
                    org.json.JSONObject myTemps = new org.json.JSONObject(iterate.next().toString());
                    Temperature newTemp = new Temperature(myTemps.getString("period"), myTemps.getInt("temperature"));
//                    System.out.println(newTemp.period +" , " + newTemp.temperature);  //Print test - data valid
                    subscriberTemp.add(newTemp);
                }
                Subscriber newOne = new Subscriber(myResponse.getString("subscriber"), myResponse.getString("homeId"), myResponse.getString("boilerType"), myResponse.getString("airConditionerType"), subscriberTemp);
                subscriberList.add(newOne);
//                newOne.printSubscriber();     //Print test - data valid!
                subscriberTemp.clear();
//                subscriberList.get(0).printSubscriber();  //Print test - data valid!
            }
            this.subscribers = new ArrayList<Subscriber>(subscriberList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }
    @Override
   public List<Subscriber> loadSubscribersToList(String fajlNev) throws Exception {
        JSONParser parser = new JSONParser();
        List<Subscriber> subscriberList = new ArrayList<Subscriber>();

        try {
            Object obj = parser.parse(new FileReader(fajlNev));
            List<Temperature> subscriberTemp = new ArrayList<Temperature>();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
            JSONArray subscribers = (JSONArray) jsonObject.get("subscribers");
            Iterator<org.json.simple.JSONObject> iterator = subscribers.iterator();

            while (iterator.hasNext()) {
                org.json.simple.JSONObject valasz = new org.json.simple.JSONObject(iterator.next());
                org.json.JSONObject myResponse = new org.json.JSONObject(valasz.toString());
                JSONArray newArray = (JSONArray) valasz.get("temperatures");
                Iterator<org.json.simple.JSONObject> iterate = newArray.iterator();

                while(iterate.hasNext()) {
                    org.json.JSONObject myTemps = new org.json.JSONObject(iterate.next().toString());
                    Temperature newTemp = new Temperature(myTemps.getString("period"), myTemps.getInt("temperature"));
//                    System.out.println(newTemp.period +" , " + newTemp.temperature);  //Print test - data valid
                    subscriberTemp.add(newTemp);
                }
                Subscriber newOne = new Subscriber(myResponse.getString("subscriber"), myResponse.getString("homeId"), myResponse.getString("boilerType"), myResponse.getString("airConditionerType"), subscriberTemp);
//                newOne.printSubscriber();     //Print test - data valid
                subscriberList.add(newOne);
                subscriberTemp.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriberList;
   }

}



