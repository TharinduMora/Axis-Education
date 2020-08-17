package com.teamtrace.axiseducation.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Model {
    public static final int SCALE = 20;
    public static final int PRECISION = 5;
    public static final String VALUE_TYPE = "DECIMAL(20, 5) DEFAULT '0'";

    public static final int EXPIRY_NONE = -1;
    public static final int EXPIRY_IN_DAY = 12 * 60 * 60 * 1000;
    public static final int EXPIRY_IN_HALF_DAY = 6 * 60 * 60 * 1000;
    public static final int EXPIRY_IN_HOUR = 60 * 60 * 1000;
    public static final int EXPIRY_IN_MINUTES = 10 * 60 * 1000;

    public static final int SIZE_SMALL = 10;
    public static final int SIZE_MEDIUM = 100;
    public static final int SIZE_LARGE = 1000;
    public static final int SIZE_EXTRA_LARGE = 10000;
    public static final int SIZE_ULTRA_LARGE = 100000;
}
