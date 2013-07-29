/***********************************************************************************
 *
 * Copyright 2013 - 2013 Yota Lab LLC, Russia
 * Copyright 2013 - 2013 Seconca Holdings Limited, Cyprus
 *
 *  This source code is Yota Lab Confidential Proprietary
 *  This software is protected by copyright.  All rights and titles are reserved.
 *  You shall not use, copy, distribute, modify, decompile, disassemble or reverse
 *  engineer the software. Otherwise this violation would be treated by law and
 *  would be subject to legal prosecution.  Legal use of the software provides
 *  receipt of a license from the right holder only.
 *
 *
 ************************************************************************************/

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
