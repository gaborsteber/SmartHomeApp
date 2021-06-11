package com.company;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class GetDataFromServer {

//    public static void getDataFrom() throws Exception {
//        String url = "http://193.6.19.58:8182/smarthome/KD34AF24DS";
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
////      int responseCode = con.getResponseCode();
////      System.out.println("Response Code : " + responseCode);
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
////      System.out.println(response.toString());
//        JSONObject responseFromServer = new JSONObject(response.toString());
////      System.out.println("sessionId- "+responseFromServer.getString("sessionId"));
////      System.out.println("temperature- "+responseFromServer.getInt("temperature"));
////      System.out.println("boilerState- "+responseFromServer.getBoolean("boilerState"));
////      System.out.println("airConditionerState- "+responseFromServer.getBoolean("airConditionerState"));
//    }
////      Static url test - Server response worked!

    public static void getDataFromWithUrl(String homeId) throws Exception {
        String url = "http://193.6.19.58:8182/smarthome/"+ homeId;
        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//      int responseCode = con.getResponseCode();
//      System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
//      System.out.println(response.toString());
        JSONObject responseFromServer = new JSONObject(response.toString());
//      System.out.println("sessionId- "+responseFromServer.getString("sessionId"));
//      System.out.println("temperature- "+responseFromServer.getInt("temperature"));
//      System.out.println("boilerState- "+responseFromServer.getBoolean("boilerState"));
//      System.out.println("airConditionerState- "+responseFromServer.getBoolean("airConditionerState"));
    }
//      Pass homeId by parameter - Server response worked!

}