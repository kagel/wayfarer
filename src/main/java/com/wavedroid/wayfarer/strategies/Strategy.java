package com.wavedroid.wayfarer.strategies;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;

/**
 * @author DKhvatov
 */
public interface Strategy {

    CompleteVenue nextVenue(FoursquareApi api, CompactVenue venue, Number... params) throws FoursquareApiException;

}
