package com.wavedroid.wayfarer.manners;

import com.wavedroid.wayfarer.filters.VenueFilter;
import com.wavedroid.wayfarer.strategies.NorthEast;
import com.wavedroid.wayfarer.strategies.Strategy;

/**
 * @author DKhvatov
 */
public class Straightforward implements Manner {

    private VenueFilter[] filters;

    public Straightforward(VenueFilter[] filters) {
        this.filters = filters;
    }

    @Override
    public Strategy getStrategy() {
        return new NorthEast(filters);
    }
}
