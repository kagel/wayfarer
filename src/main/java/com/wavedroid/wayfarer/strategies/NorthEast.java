package com.wavedroid.wayfarer.strategies;

import com.wavedroid.wayfarer.FoursquareUtils;
import com.wavedroid.wayfarer.ambitions.Ambition;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

/**
 * @author DKhvatov
 */
public class NorthEast extends SelectiveStrategy {

    public NorthEast(Ambition[] ambitions) {
        super(ambitions);
    }

    @Override
    protected CompactVenue[] nextVenues(FoursquareApi api, CompactVenue venue, int counter, double latOffset, double lonOffset) throws FoursquareApiException {
        String ll = FoursquareUtils.getLatLon(venue, latOffset, lonOffset);
        Result<VenuesSearchResult> vsr = api.venuesSearch(ll, 1.0, 0.0, 1.0, "", 50, "checkin", null, null, null, null);
        VenuesSearchResult searchResult = vsr.getResult();
        if (searchResult == null) {
            System.out.println(tab(counter) + "Search error, waiting");
            try {
                Thread.sleep(10000 * counter);
            } catch (InterruptedException ignored) {
            }
            return nextVenues(api, venue, ++counter, latOffset, lonOffset);
        }
        CompactVenue[] venues = searchResult.getVenues();

        if (venues.length == 0) {
            System.out.println(tab(counter) + "Nothing found, waiting + increasing step");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
            return nextVenues(api, venue, ++counter, latOffset * 1.25, lonOffset * 1.25);
        }
        return venues;
    }

    @Override
    protected double getLonOffset() {
        return 0.005;
    }

    @Override
    protected double getLatOffset() {
        return 0.005;
    }
}

