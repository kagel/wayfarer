package com.wavedroid.wayfarer.manners;

import com.wavedroid.wayfarer.strategies.NorthEast;
import com.wavedroid.wayfarer.strategies.Strategy;

/**
 * @author DKhvatov
 */
public class Straightforward implements Manner {
    @Override
    public Strategy getStrategy() {
        return new NorthEast();
    }
}
