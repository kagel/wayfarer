package com.wavedroid.wayfarer;

import java.util.Random;

import com.wavedroid.wayfarer.goals.Goal;
import com.wavedroid.wayfarer.strategies.Strategy;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;

import static com.wavedroid.wayfarer.WayfarerProperties.getAccessToken;
import static com.wavedroid.wayfarer.WayfarerProperties.getClientId;
import static com.wavedroid.wayfarer.WayfarerProperties.getClientSecret;
import static com.wavedroid.wayfarer.WayfarerProperties.getRedirectUrl;
import static com.wavedroid.wayfarer.WayfarerProperties.getStartVenueId;
import static com.wavedroid.wayfarer.WayfarerProperties.getUserId;
import static com.wavedroid.wayfarer.WayfarerProperties.isDebug;

/**
 * @author DKhvatov
 */
public class Wayfarer {


    public static void start(Goal goal, Strategy strategy) throws FoursquareApiException, InterruptedException {

        double latOffset = 0.005;
        double lonOffset = 0.005;

        FoursquareApi api = new FoursquareApi(getClientId(), getClientSecret(), getRedirectUrl());
        api.setoAuthToken(getAccessToken());

        Checkin lastCheckin = FoursquareUtils.getLastCheckin(api, getUserId());
        CompactVenue venue;
        if (lastCheckin != null) {
            venue = lastCheckin.getVenue();
        } else {
            Result<CompleteVenue> venueResult = api.venue(getStartVenueId());
            venue = venueResult.getResult();
        }

        Random rnd = new Random(Math.abs(Wayfarer.class.hashCode()));
        try {
            while (!goal.isComplete(venue)) {

                venue = strategy.nextVenue(api, venue, latOffset, lonOffset, 0);

                if (!isDebug()) {
                    Result<Checkin> checkinResult = api.checkinsAdd(venue.getId(), null, null, "public", FoursquareUtils.getLatLon(venue, 0.0, 0.0), 1.0, 0.0, 1.0);
                    if (checkinResult.getMeta().getCode() != 200) {
                        System.out.println("checkin error: " + checkinResult.getMeta().getErrorDetail() + "(" + checkinResult.getMeta().getErrorType() + ")");
                    } else
                        CheckinsCache.instance.add(venue.getId());
                }

                if (!isDebug())
                    Thread.sleep(rnd.nextInt(2880000) + 720000);
            }
            System.out.println(goal.completionMessage());
        } catch (Throwable t) {
            System.out.println(goal.failureMessage());
        }
    }


}
