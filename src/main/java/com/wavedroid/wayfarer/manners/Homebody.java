package com.wavedroid.wayfarer.manners;

import com.wavedroid.wayfarer.ambitions.Ambition;
import com.wavedroid.wayfarer.ambitions.Amoeba;
import com.wavedroid.wayfarer.strategies.FixedList;
import com.wavedroid.wayfarer.strategies.Strategy;
import fi.foyt.foursquare.api.entities.CompleteVenue;

/**
 * Date: 26.10.13
 * Time: 22:57
 *
 * @author dkhvatov
 */
public class Homebody implements Manner {

    private FixedList strategy;

    public Homebody(CompleteVenue[] homeVenuesList) {
        this.strategy = new FixedList(homeVenuesList, new Ambition[]{new Amoeba()});
    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public int getDelay() {
        return 0; // check-in at home as fast as you can
    }
}
