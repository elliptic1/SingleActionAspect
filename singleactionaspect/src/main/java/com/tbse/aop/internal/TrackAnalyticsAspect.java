/*
 * Copyright 2015 Feng Dai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tbse.aop.internal;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TrackAnalyticsAspect {
    private static final String POINTCUT_ON_CLICK_METHOD
            = "execution(void android.view.View.OnClickListener+.onClick(android.view.View))";
    private static final String POINTCUT_ON_ITEM_CLICK_METHOD
            = "execution(void android.widget.AdapterView.OnItemClickListener+.onItemClick(android.widget.AdapterView, android.view.View, int, long))";

    private static String value1;
    private static String value2;
    private static String value3;

    @Pointcut(POINTCUT_ON_CLICK_METHOD + " || " + POINTCUT_ON_ITEM_CLICK_METHOD)
    public void clickMethod() {
        Log.d("aop", "clickMethod");
    }

    @Pointcut("clickMethod() && execution(@com.tbse.aop.internal.SingleActionFamily * *(..))")
    public void annotatedClickMethod() {
        Log.d("aop", "annotatedClickMethod");
    }

    @Pointcut("clickMethod() && within(@com.tbse.aop.internal.SingleActionFamily *)")
    public void clickMethodInsideAnnotatedType() {
        Log.d("aop", "clickMethodInsideAnnotatedtype");
    }

    @Around("annotatedClickMethod() || clickMethodInsideAnnotatedType()")
    public void track(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        TrackAnalytics trackAnalytics = signature.getMethod()
                .getAnnotation(TrackAnalytics.class);
        value1 = trackAnalytics.value1();
        value2 = trackAnalytics.value2();
        value3 = trackAnalytics.value3();

        Log.d("aop", "Track analytics with info " + value1 + ", " + value2 + ", " + value3);

        joinPoint.proceed();

    }
}
