package com.wavedroid.wayfarer;

import com.wavedroid.wayfarer.goals.Death;
import com.wavedroid.wayfarer.manners.Straightforward;
import fi.foyt.foursquare.api.FoursquareApiException;

/**
 * @author DKhvatov
 */
public class WayfarerLauncher {

    public static void main(String[] args) throws FoursquareApiException, InterruptedException {
        Wayfarer.start(new Death(), new Straightforward());
    }
}
