package com.company;

public class Driver implements iDriver {
    @Override
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
        return 0; //semmi sem tortenik
    }
}


