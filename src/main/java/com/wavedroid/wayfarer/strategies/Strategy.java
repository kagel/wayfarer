package com.wavedroid.wayfarer.strategies;

import com.wavedroid.wayfarer.ambitions.Ambition;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;

/**
 * @author DKhvatov
 */
public interface Strategy {

    CompleteVenue nextVenue(CompactVenue venue, int counter, Number... params) throws FoursquareApiException;

    Ambition[] getAmbitions();

}
