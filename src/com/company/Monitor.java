package com.company;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Monitor implements iMonitor{
    private Session monitoredHome;


    @Override
    public void getSession(String homeId) throws Exception {
            String url = "http://193.6.19.58:8182/smarthome/"+ homeId;
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
//          int responseCode = con.getResponseCode();
//          System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
//          System.out.println(response.toString());
            JSONObject responseFromServer = new JSONObject(response.toString());
            String sessionId = responseFromServer.getString("sessionId");
            int temperature = responseFromServer.getInt("temperature");
            boolean boilerState = responseFromServer.getBoolean("boilerState");
            boolean airConditionerState = responseFromServer.getBoolean("airConditionerState");
            this.monitoredHome = new Session(sessionId,temperature,boilerState,airConditionerState);
//          System.out.println("sessionId- "+responseFromServer.getString("sessionId"));
//          System.out.println("temperature- "+responseFromServer.getInt("temperature"));
//          System.out.println("boilerState- "+responseFromServer.getBoolean("boilerState"));
//          System.out.println("airConditionerState- "+responseFromServer.getBoolean("airConditionerState"));
    }
//          Pass homeId by parameter - Server response worked!

    @Override
    public Session getMonitoredHome() {
        return monitoredHome;
    }

}
