package com.wavedroid.wayfarer.goals;

import fi.foyt.foursquare.api.entities.CompactVenue;

/**
 * @author DKhvatov
 */
public class Death implements Goal {

    @Override
    public boolean isComplete(CompactVenue venue) {
        return false; // you never know when you are dead.
    }

    @Override
    public String completionMessage() {
        return "Starved to death :(";
    }

    @Override
    public String failureMessage() {
        return "Died trying to die";
    }
}
