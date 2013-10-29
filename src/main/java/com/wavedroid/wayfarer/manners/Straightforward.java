package com.wavedroid.wayfarer.manners;

import com.wavedroid.wayfarer.ambitions.Ambition;
import com.wavedroid.wayfarer.strategies.NorthEast;
import com.wavedroid.wayfarer.strategies.Strategy;

/**
 * @author DKhvatov
 */
public class Straightforward implements Manner {

    private Ambition[] ambitions;

    public Straightforward(Ambition[] ambitions) {
        this.ambitions = ambitions;
    }

    @Override
    public Strategy getStrategy() {
        return new NorthEast(ambitions);
    }

    @Override
    public int getDelay() {
        return 2880000;
    }
}
