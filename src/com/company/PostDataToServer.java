package com.company;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;


public class PostDataToServer {

    public static void postDataTo() throws Exception {
        /*TODO: a postDataToServer osztaly nem kell, hanem az alabbi a PostdataTo bekerul az Driverbe, ahol
        majd a konkret utasitasokat kuldi ki. Át kell alakaítani, aztan refactorral torolni kell. A neve majd
        a feladat 5.3 pontja szerint CommandService lesz.*/
        URL url = new URL("http://193.6.19.58:8182/smarthome/KD34AF24DS");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/plain");
        conn.setDoOutput(true);
        String message;
        JSONObject item = new JSONObject();
        item.put("boilerCommand", "bX3434");
        item.put("homeId", "KD34AF24DS");
        item.put("airConditionerCommand", "bX5676");
        System.out.println(item.getString("boilerCommand"));
        message = item.toString();
      System.out.println(message);
        try(OutputStream os = conn.getOutputStream()){
            byte[] input = message.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
}