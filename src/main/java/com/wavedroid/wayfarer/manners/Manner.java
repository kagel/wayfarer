package com.wavedroid.wayfarer.manners;

import com.wavedroid.wayfarer.strategies.Strategy;

/**
 * @author DKhvatov
 */
public interface Manner {

    Strategy getStrategy();

    int getDelay();

}
