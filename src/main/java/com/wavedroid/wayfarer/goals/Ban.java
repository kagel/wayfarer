package com.wavedroid.wayfarer.goals;

import fi.foyt.foursquare.api.ResultMeta;
import fi.foyt.foursquare.api.entities.CompactVenue;

import static com.wavedroid.wayfarer.FoursquareApiWrapper.api;

/**
 * Date: 26.10.13
 * Time: 22:55
 *
 * @author dkhvatov
 */
public class Ban implements Goal {
    @Override
    public boolean isComplete(CompactVenue venue) {
        ResultMeta checkinResult = api.getLastCheckinResult();
        if (checkinResult.getCode() != 0) {
            if (checkinResult.getErrorType().equals("rate_limit_exceeded"))
                return true;
        }
        return false;
    }

    @Override
    public String completionMessage() {
        return "Got banned.";
    }

    @Override
    public String failureMessage() {
        return "Am I banned?";
    }
}
