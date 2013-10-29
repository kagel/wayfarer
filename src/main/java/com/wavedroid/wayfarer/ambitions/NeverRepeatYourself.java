package com.wavedroid.wayfarer.ambitions;

import com.wavedroid.wayfarer.CheckinsCache;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;

import static com.wavedroid.wayfarer.FoursquareApiWrapper.api;

/**
 * @author DKhvatov
 */
public class NeverRepeatYourself implements Ambition {
    @Override
    public boolean fulfill(CompactVenue venue) throws FoursquareApiException {
        if (CheckinsCache.instance.contains(venue.getId())) return false;
        Result<CompleteVenue> venueResult = api.venue(venue.getId());
        if (venueResult == null) return false;
        CompleteVenue completeVenue = venueResult.getResult();
        if (completeVenue == null) return false;

        if (completeVenue.getBeenHere().getCount() > 0) {
            CheckinsCache.instance.add(venue.getId());
            return false;
        }

        return true;
    }

    @Override
    public String msg(CompactVenue venue) {
        return "has been before: " + venue.getName();
    }
}
