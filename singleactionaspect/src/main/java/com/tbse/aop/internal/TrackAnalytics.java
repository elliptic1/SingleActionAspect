package com.tbse.aop.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by smitt345 on 11/20/16.
 */

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface TrackAnalytics {
    String value1();
    String value2();
    String value3();
}
