package com.company;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Driver implements iDriver {

    // ide ele kerul majd a map<string<list<string>>>, ami az eszkoz es a hozza tartozo parancslista,
    // ebbol valaszt majd a metodus a map kulcsa szerint, majd vegul csinal egy hivast az eszkozre

    //konstructor
    public Driver() {
        List<String> kazan1 = new ArrayList<String>();
        kazan1.add("parancs1");
        kazan1.add("parancs2");
        commands.put("Kazan1", kazan1);
        // ... igy tovabb az osszes kazanra

    };
    //Ez tarolja a mapet az eszkoz adatokkal...
    Map<String, List<String>> commands;

    @Override //annotation
    public int sendCommand(Subscriber subs, boolean boilerCommand, boolean airconCommand) throws Exception {
        //visszaad valamit a proba kedveert
        if (boilerCommand || airconCommand) {
            if (boilerCommand && airconCommand) {
                //
                return 100; //ha mindketto mukodik, akkor mindketto leall
            } else if (boilerCommand) {
                //
                return 100; //boiler leall
            } else if (airconCommand) {
                //
                return 100; //aircon leall
            }
        }
        //ide logol majd
        return 0; //semmi sem tortenik
    }

    public void commandSend(List<Subscriber> subscribersList) throws Exception {
        for(int i=0; i<subscribersList.size(); i++) {
            iMonitor monitoredHome = new Monitor();
            Session actualHome;
            monitoredHome.getSession(subscribersList.get(i).getHomeId());
            actualHome = monitoredHome.getMonitoredHome();
            URL url = new URL("http://193.6.19.58:8182/smarthome/" + subscribersList.get(i).getHomeId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            String message;
            JSONObject item = new JSONObject();
            item.put("homeId", subscribersList.get(i).getHomeId());
            //TODO: össze kellene hasonlítani, hogy az aktuális időponthoz tartozó beállított hőmérséklethez képest megfelel-e a kapott hőmérséklet.
            //TODO: Ha igen akkor nincs teendő, ha kisebb a hőmérséklet akkor a kazán típusához megefelő indító parancs küldése, légkondi leállítása és fordítva! KÉSZ
            System.out.println("Kívánt hőmérséklet: " + Subscriber.getTemperatureForNow(subscribersList.get(i)) + " - Lekérdezett hőmérséklet: " + actualHome.getTemperature());
            if (Subscriber.getTemperatureForNow(subscribersList.get(i)) >= actualHome.getTemperature())  //A kértnél alacsonyabb a hőmérésklet, fűtés szükséges!
            {
                System.out.println("Fűtés szükséges!");
                //System.out.println(subscribersList.get(i).getBoilerType());
                switch(subscribersList.get(i).getBoilerTypeInt()){
                    case 1:
                        System.out.println("Boiler 1200W");
                        item.put("boilerCommand", "bX3434");
                        break;
                    case 2:
                        System.out.println("Boiler p5600");
                        item.put("boilerCommand", "cX7898");
                        break;
                    default:
                        System.out.println("Ismeretlen kazán!");
                }
                switch(subscribersList.get(i).getAirTypeInt()) {
                    case 1:
                        System.out.println("Air p5600");
                        item.put("airConditionerCommand", "bX3421");
                        break;
                    case 2:
                        System.out.println("Air c320");
                        item.put("airConditionerCommand", "cX5423");
                        break;
                    default:
                        System.out.println("Ismeretlen légkondi!");
                }
            } // IF hőmérséklet összehasonlítása
            else //Magasabb a hőmérséklet a kértnél, hűtés szükséges!
            {
                System.out.println("Hűtés szükséges!");
                //System.out.println(subscribersList.get(i).getBoilerType());
                switch(subscribersList.get(i).getBoilerTypeInt()){
                    case 1:
                        System.out.println("Boiler 1200W");
                        item.put("boilerCommand", "bX1232");
                        break;
                    case 2:
                        System.out.println("Boiler p5600");
                        item.put("boilerCommand", "cX3452");
                        break;
                    default:
                        System.out.println("Ismeretlen kazán!");
                }
                switch(subscribersList.get(i).getAirTypeInt()) {
                    case 1:
                        System.out.println("Air p5600");
                        item.put("airConditionerCommand", "bX5676");
                        break;
                    case 2:
                        System.out.println("Air c320");
                        item.put("airConditionerCommand", "cX3452");
                        break;
                    default:
                        System.out.println("Ismeretlen légkondi!");
                }
            }
//            System.out.println(item.getString("boilerCommand"));
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
                //System.out.println(response.toString());
                if (response.toString().equals("100"))
                {
                    System.out.println("A szerver visszaigazolta a sikeres beállítást!");
                }
                if (response.toString().equals("101"))
                {
                    System.out.println("A szerver hibás parancs választ adott!");
                }
            }
        }
    }
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


