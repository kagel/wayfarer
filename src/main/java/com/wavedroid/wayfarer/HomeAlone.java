package com.wavedroid.wayfarer;

import java.util.List;

import com.wavedroid.wayfarer.goals.Ban;
import com.wavedroid.wayfarer.manners.Homebody;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompleteVenue;

import static com.wavedroid.wayfarer.FoursquareApiWrapper.api;
import static com.wavedroid.wayfarer.WayfarerProperties.getHomeVenues;

/**
 * Date: 26.10.13
 * Time: 22:54
 *
 * @author dkhvatov
 */
public class HomeAlone {

    public static void main(String[] args) throws FoursquareApiException, InterruptedException {

        List<String> ids = getHomeVenues();
        CompleteVenue homeVenues[] = new CompleteVenue[ids.size()];
        int i = 0;
        for (String id : ids) {
            Result<CompleteVenue> venueResult = api.venue(id);
            CompleteVenue venue = venueResult.getResult();
            homeVenues[i++] = venue;
        }

        Wayfarer.start(
                new Ban(),
                new Homebody(homeVenues));
    }
}
