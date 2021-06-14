package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Driver implements iDriver {

    // ide ele kerul majd a map<string<list<string>>>, ami az eszkoz es a hozza tartozo parancslista,
    // ebbol valaszt majd a metodus a map kulcsa szerint, majd vegul csinal egy hivast az eszkozre
    //Ez tarolja a mapet az eszkoz adatokkal...
    Map<String, List<String>> commands = new HashMap<String, List<String>>(); // HashMap típust kellet használni, a kulcs szerinti lekérdezéshez!

    public Map<String, List<String>> getCommands() {
        return commands;
    }
//Map<String, List<String>> aCommands;

    //konstructor

    public Driver() {
        List<String> boiler1 = new ArrayList<String>();
        boiler1.add("bX3434");
        boiler1.add("bX1232");
       // System.out.println(boiler1.get(0));
        List<String> boiler2 = new ArrayList<String>();
        boiler2.add("cX7898");
        boiler2.add("cX3452");
        List<String> air1 = new ArrayList<String>();
        air1.add("bX5676");
        air1.add("bX3421");
        List<String> air2 = new ArrayList<String>();
        air2.add("cX3452");
        air2.add("cX5423");
        try {
            assert commands != null;
            commands.put("Boiler 1200W", boiler1);//az eszkoz neve pl. Subscriber.boilerneve
            commands.put("Boiler p5600", boiler2);
            commands.put("Air p5600", air1);
            commands.put("Air c320", air2);
            //System.out.println(this.commands.get("Boiler 1200W").get(0));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
//        String v = kazan1.get(1);
//         ... igy tovabb az osszes kazanra
//        String parancs = (bCommands.get("Kazan1").get(0)); //elindul parancs
    }



    @Override //annotation
    public int sendCommand(Subscriber subs, boolean boilerCommand, boolean airconCommand) throws Exception {
        iDriver sendToHome = new Driver();
        URL url = new URL("http://193.6.19.58:8182/smarthome/" + subs.getHomeId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/plain");
        conn.setDoOutput(true);
        String message;
        JSONObject item = new JSONObject();
        //System.out.println(sendToHome.getCommands().get(subs.getBoilerType()).get(0));
        item.put("homeId", subs.getHomeId());
        try {
            if (boilerCommand) {
                item.put("boilerCommand", sendToHome.getCommands().get(subs.getBoilerType()).get(0));
            } else {
                item.put("boilerCommand", sendToHome.getCommands().get(subs.getBoilerType()).get(1));
            }
            if (airconCommand) {
                item.put("airConditionerCommand", sendToHome.getCommands().get(subs.getAirConditionerType()).get(0));
            } else {
                item.put("airConditionerCommand", sendToHome.getCommands().get(subs.getAirConditionerType()).get(1));
            }
        }
        catch (Exception e)
        {
           // e.printStackTrace();
        }
        message = item.toString();
        System.out.println(message);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = message.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return parseInt(response.toString());
        }
    }


    //TODO: át kell kerülnie a controllerbe
    // a hívások mennek a driver metóduson keresztül...
//    public void commandSend(List<Subscriber> subscribersList) throws Exception { //controllCheckService
//        for (int i = 0; i < subscribersList.size(); i++) {
//            iMonitor monitoredHome = new Monitor();
//            Session actualHome;
//            monitoredHome.getSession(subscribersList.get(i).getHomeId());
//            actualHome = monitoredHome.getMonitoredHome();
//            URL url = new URL("http://193.6.19.58:8182/smarthome/" + subscribersList.get(i).getHomeId());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "text/plain");
//            conn.setDoOutput(true);
//            String message;
//            JSONObject item = new JSONObject();
//            item.put("homeId", subscribersList.get(i).getHomeId());
//            //TODO: össze kellene hasonlítani, hogy az aktuális időponthoz tartozó beállított hőmérséklethez képest megfelel-e a kapott hőmérséklet.
//            //TODO: Ha igen akkor nincs teendő, ha kisebb a hőmérséklet akkor a kazán típusához megefelő indító parancs küldése, légkondi leállítása és fordítva! KÉSZ
//            System.out.println("Kívánt hőmérséklet: " + Subscriber.getTemperatureForNow(subscribersList.get(i)) + " - Lekérdezett hőmérséklet: " + actualHome.getTemperature());
//            if (Subscriber.getTemperatureForNow(subscribersList.get(i)) >= actualHome.getTemperature())  //A kértnél alacsonyabb a hőmérésklet, fűtés szükséges!
//            {
//                System.out.println("Fűtés szükséges!");
//                //System.out.println(subscribersList.get(i).getBoilerType());
//                switch (subscribersList.get(i).getBoilerTypeInt()) {
//                    case 1:
//                        System.out.println("Boiler 1200W");
//                        item.put("boilerCommand", "bX3434");
//                        break;
//                    case 2:
//                        System.out.println("Boiler p5600");
//                        item.put("boilerCommand", "cX7898");
//                        break;
//                    default:
//                        System.out.println("Ismeretlen kazán!");
//                }
//                switch (subscribersList.get(i).getAirTypeInt()) {
//                    case 1:
//                        System.out.println("Air p5600");
//                        item.put("airConditionerCommand", "bX3421");
//                        break;
//                    case 2:
//                        System.out.println("Air c320");
//                        item.put("airConditionerCommand", "cX5423");
//                        break;
//                    default:
//                        System.out.println("Ismeretlen légkondi!");
//                }
//            } // IF hőmérséklet összehasonlítása
//            else //Magasabb a hőmérséklet a kértnél, hűtés szükséges!
//            {
//                System.out.println("Hűtés szükséges!");
//                //System.out.println(subscribersList.get(i).getBoilerType());
//                switch (subscribersList.get(i).getBoilerTypeInt()) {
//                    case 1:
//                        System.out.println("Boiler 1200W");
//                        item.put("boilerCommand", "bX1232");
//                        break;
//                    case 2:
//                        System.out.println("Boiler p5600");
//                        item.put("boilerCommand", "cX3452");
//                        break;
//                    default:
//                        System.out.println("Ismeretlen kazán!");
//                }
//                switch (subscribersList.get(i).getAirTypeInt()) {
//                    case 1:
//                        System.out.println("Air p5600");
//                        item.put("airConditionerCommand", "bX5676");
//                        break;
//                    case 2:
//                        System.out.println("Air c320");
//                        item.put("airConditionerCommand", "cX3452");
//                        break;
//                    default:
//                        System.out.println("Ismeretlen légkondi!");
//                }
//            }
////            System.out.println(item.getString("boilerCommand"));
//            message = item.toString();
//            System.out.println(message);
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = message.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//            try (BufferedReader br = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                //System.out.println(response.toString());
//                if (response.toString().equals("100")) {
//                    System.out.println("A szerver visszaigazolta a sikeres beállítást!");
//                }
//                if (response.toString().equals("101")) {
//                    System.out.println("A szerver hibás parancs választ adott!");
//                }
//            }
//        }
//    }
}

//        http://193.6.19.58:8182/smarthome/KD34AF24DS
//        Kívánt hőmérséklet: 22.0 - Lekérdezett hőmérséklet: 20.0
//        Fűtés szükséges!
//        Boiler 1200W
//        Air p5600
//        {"airConditionerCommand":"bX3421","boilerCommand":"bX3434","homeId":"KD34AF24DS"}
//        100
//        http://193.6.19.58:8182/smarthome/LRKE4LE234D
//        Kívánt hőmérséklet: 21.5 - Lekérdezett hőmérséklet: 23.0
//        Hűtés szükséges!
//        Ismeretlen kazán!
//        Ismeretlen légkondi!
//        {"homeId":"LRKE4LE234D"}
//        101
//        http://193.6.19.58:8182/smarthome/TE34WS3224
//        Kívánt hőmérséklet: 21.0 - Lekérdezett hőmérséklet: 15.0
//        Fűtés szükséges!
//        Boiler p5600
//        Ismeretlen légkondi!
//        {"boilerCommand":"cX7898","homeId":"TE34WS3224"}
//        100
//        http://193.6.19.58:8182/smarthome/TTLR3234ZZ
//        Kívánt hőmérséklet: 20.0 - Lekérdezett hőmérséklet: 21.0
//        Hűtés szükséges!
//        Ismeretlen kazán!
//        Ismeretlen légkondi!
//        {"homeId":"TTLR3234ZZ"}
//        101
//        http://193.6.19.58:8182/smarthome/EEI23EE12D
//        Kívánt hőmérséklet: 22.0 - Lekérdezett hőmérséklet: 15.0
//        Fűtés szükséges!
//        Ismeretlen kazán!
//        Ismeretlen légkondi!
//        {"homeId":"EEI23EE12D"}
//        101
//
//        Process finished with exit code 0


