package com.wavedroid.wayfarer.ambitions;

import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;

/**
 * Date: 26.10.13
 * Time: 22:59
 *
 * @author dkhvatov
 */
public class Amoeba implements Ambition {
    @Override
    public boolean fulfill(CompactVenue venue) throws FoursquareApiException {
        return true;
    }

    @Override
    public String msg(CompactVenue venue) {
        return "this will never happen.";
    }
}
