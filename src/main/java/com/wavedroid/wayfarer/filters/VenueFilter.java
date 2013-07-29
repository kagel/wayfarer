package com.wavedroid.wayfarer.filters;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;

/**
 * @author DKhvatov
 */
public interface VenueFilter {

    boolean filter(FoursquareApi api, CompactVenue venue) throws FoursquareApiException;

    String msg(CompactVenue venue);

}
