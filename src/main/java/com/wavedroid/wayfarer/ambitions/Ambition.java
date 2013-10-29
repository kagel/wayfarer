package com.wavedroid.wayfarer.ambitions;

import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;

/**
 * @author DKhvatov
 */
public interface Ambition {

    boolean fulfill(CompactVenue venue) throws FoursquareApiException;

    String msg(CompactVenue venue);

}
