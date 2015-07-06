package com.plivo.castleblack;
import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.Outgoing;


/**
 * Created by ramya on 4/26/15.
 */
public class DataHolder {
    private static Endpoint endpoint ;
    private static Outgoing outgoing;


    public static Endpoint getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(Endpoint endpt) {
        endpoint = endpt;
    }

    public static Outgoing getOutgoing(){
        return outgoing;
    }

    public static void setOutgoing(Outgoing outgoing) {
        DataHolder.outgoing = outgoing;
    }
}
