package com.wavedroid.wayfarer.goals;

import fi.foyt.foursquare.api.entities.CompactVenue;

/**
 * @author DKhvatov
 */
public interface Goal {

    boolean isComplete(CompactVenue venue);
    String completionMessage();
    String failureMessage();

}
