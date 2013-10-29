package com.wavedroid.wayfarer.strategies;

import com.wavedroid.wayfarer.ambitions.Ambition;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;

/**
 * Date: 26.10.13
 * Time: 23:03
 *
 * @author dkhvatov
 */
public class FixedList implements Strategy {

    private CompleteVenue[] venuesList;
    private int currentVenue = 0;
    private Ambition[] ambitions;

    public FixedList(CompleteVenue[] venuesList, Ambition[] ambitions) {
        this.venuesList = venuesList;
        this.ambitions = ambitions;
    }

    @Override
    public CompleteVenue nextVenue(CompactVenue venue, int counter, Number... params) throws FoursquareApiException {
        CompleteVenue next = venuesList[currentVenue];
        currentVenue++;
        if (currentVenue == venuesList.length)
            currentVenue = 0;
        return next;
    }

    @Override
    public Ambition[] getAmbitions() {
        return ambitions;
    }
}
