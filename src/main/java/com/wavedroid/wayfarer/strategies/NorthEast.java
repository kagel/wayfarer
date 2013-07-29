package com.wavedroid.wayfarer.strategies;

import java.util.Arrays;

import com.wavedroid.wayfarer.CheckinsCache;
import com.wavedroid.wayfarer.FoursquareUtils;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

/**
 * @author DKhvatov
 */
public class NorthEast implements Strategy {

    @Override
    public CompleteVenue nextVenue(FoursquareApi api, CompactVenue venue, Number... params) throws FoursquareApiException {
        double latOffset = (double) params[0];
        double lonOffset = (double) params[1];
        int counter = (int) params[2];
        return NorthEast.nextVenue(api, venue, latOffset, lonOffset, counter);
    }

    private static CompleteVenue nextVenue(FoursquareApi api, CompactVenue venue, double latOffset, double lonOffset, int counter) throws FoursquareApiException {
        System.out.println(tab(counter) + "searching for next venue, current venue: " + FoursquareUtils.printVenue(venue) + ", lat step: " + latOffset + ", lon step: " + lonOffset);
        if (counter > 20) {
            return null; // give up
        }
        String ll = FoursquareUtils.getLatLon(venue, latOffset, lonOffset);
        Result<VenuesSearchResult> vsr = api.venuesSearch(ll, 1.0, 0.0, 1.0, "", 50, "checkin", null, null, null, null);
        VenuesSearchResult searchResult = vsr.getResult();
        if (searchResult == null) {
            System.out.println(tab(counter) + "Search error, waiting");
            try {
                Thread.sleep(10000 * counter);
            } catch (InterruptedException ignored) {
            }
            return nextVenue(api, venue, latOffset, lonOffset, ++counter);
        }
        CompactVenue[] venues = searchResult.getVenues();
        if (venues.length == 0) {
            System.out.println(tab(counter) + "Nothing found, waiting + increasing step");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
            return nextVenue(api, venue, latOffset * 1.25, lonOffset * 1.25, ++counter);
        }

        CompleteVenue result;
        for (CompactVenue compactVenue : venues) {
            Result<CompleteVenue> venueResult = api.venue(compactVenue.getId());
            result = venueResult.getResult();
            if (result == null) {
                System.out.println(tab(counter) + "null result for venue: " + compactVenue.getName());
                continue;
            }
            if (CheckinsCache.instance.contains(compactVenue.getId()) || result.getBeenHere().getCount() > 0) {
                System.out.println(tab(counter) + "has been before: " + result.getName());
                CheckinsCache.instance.add(compactVenue.getId());
                continue;
            }
            System.out.println(tab(counter) + "next venue: " + result.getName());
            return result;
        }

        System.out.println(tab(counter) + "Nothing found, increasing step");
        return nextVenue(api, venue, latOffset * 1.25, lonOffset * 1.25, ++counter);
    }

    private static String tab(int w) {
        byte[] b = new byte[w * 2];
        Arrays.fill(b, (byte) 0x20);
        return new String(b);
    }

}
