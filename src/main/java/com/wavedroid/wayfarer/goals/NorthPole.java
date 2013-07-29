package com.wavedroid.wayfarer.goals;

import fi.foyt.foursquare.api.entities.CompactVenue;

/**
 * @author DKhvatov
 */
public class NorthPole implements Goal {

    @Override
    public boolean isComplete(CompactVenue venue) {
        return venue.getLocation().getLat() > 82.7 && venue.getLocation().getLat() < 114.4;
    }

    @Override
    public String completionMessage() {
        return "freezed to death :(";
    }

    @Override
    public String failureMessage() {
        return "Died trying to die";
    }
}
