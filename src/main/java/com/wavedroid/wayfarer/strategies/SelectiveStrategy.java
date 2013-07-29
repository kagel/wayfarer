package com.wavedroid.wayfarer.strategies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.wavedroid.wayfarer.FoursquareUtils;
import com.wavedroid.wayfarer.ambitions.Ambition;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;

/**
 * @author DKhvatov
 */
public abstract class SelectiveStrategy implements Strategy {

    private final Ambition[] ambitions;

    public SelectiveStrategy(Ambition[] ambitions) {
        this.ambitions = ambitions;
    }

    @Override
    public CompleteVenue nextVenue(FoursquareApi api, CompactVenue venue, int counter, Number... params) throws FoursquareApiException {
        return nextVenue(api, venue, counter, getLatOffset(), getLonOffset());
    }

    protected abstract double getLonOffset();

    protected abstract double getLatOffset();

    protected CompleteVenue nextVenue(FoursquareApi api, CompactVenue venue, int counter, double latOffset, double lonOffset) throws FoursquareApiException {
        System.out.println(tab(counter) + "searching for next venue, current venue: " + FoursquareUtils.printVenue(venue) + ", lat step: " + latOffset + ", lon step: " + lonOffset);
        if (counter > 20) {
            return null; // give up
        }

        CompactVenue[] venues = nextVenues(api, venue, counter, latOffset, lonOffset);
        List<CompactVenue> filteredVenues = new LinkedList<>();
        for (Ambition ambition : getAmbitions()) {
            for (CompactVenue v : venues) {
                if (ambition.fulfill(api, v))
                    filteredVenues.add(v);
                else
                    System.out.println(tab(counter) + ambition.msg(v));
            }
        }

        CompleteVenue result;
        for (CompactVenue compactVenue : filteredVenues) {
            Result<CompleteVenue> venueResult = api.venue(compactVenue.getId());
            result = venueResult.getResult();
            if (result == null) {
                System.out.println(tab(counter) + "null result for venue: " + compactVenue.getName());
                continue;
            }
            System.out.println(tab(counter) + "next venue: " + result.getName());
            return result;
        }

        System.out.println(tab(counter) + "Nothing found, increasing step");
        return nextVenue(api, venue, ++counter, latOffset * 1.25, lonOffset * 1.25);
    }

    protected abstract CompactVenue[] nextVenues(FoursquareApi api, CompactVenue venue, int counter, double latOffset, double lonOffset) throws FoursquareApiException;

    protected static String tab(int w) {
        byte[] b = new byte[w * 2];
        Arrays.fill(b, (byte) 0x20);
        return new String(b);
    }


    @Override
    public Ambition[] getAmbitions() {
        return ambitions;
    }
}
