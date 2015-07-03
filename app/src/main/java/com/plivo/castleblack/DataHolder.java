package com.plivo.castleblack;
import com.plivo.endpoint.Endpoint;

/**
 * Created by ramya on 4/26/15.
 */
public class DataHolder {
    private static Endpoint endpoint ;


    public static Endpoint getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(Endpoint endpt) {
        endpoint = endpt;
    }
}
