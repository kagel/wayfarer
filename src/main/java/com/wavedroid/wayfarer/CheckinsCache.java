package com.wavedroid.wayfarer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author DKhvatov
 */
public class CheckinsCache {

    private static final Set<String> cache = new HashSet<>();
    public static final CheckinsCache instance = new CheckinsCache();

    private CheckinsCache() {
    }

    public void add(String s) {
        cache.add(s);
    }

    public boolean contains(String s) {
        return cache.contains(s);
    }

}
