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

package mocha.weaving.internal;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;

import mocha.weaving.SingleActionFamily;

@Aspect
public class Mocha {
    private static final String POINTCUT_ON_CLICK_METHOD
            = "execution(void android.view.View.OnClickListener+.onClick(android.view.View))";
    private static final String POINTCUT_ON_ITEM_CLICK_METHOD
            = "execution(void android.widget.AdapterView.OnItemClickListener+.onItemClick(android.widget.AdapterView, android.view.View, int, long))";

    private static final ArrayList<String> singleActionFamilies = new ArrayList<>();
    private static String value;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public Mocha() {
        Log.d("aop", "mocha init");
        singleActionFamilies.add("SingleAction");
    }

    @Pointcut(POINTCUT_ON_CLICK_METHOD + " || " + POINTCUT_ON_ITEM_CLICK_METHOD)
    public void clickMethod() {
        Log.d("aop", "clickMethod");
    }

    @Pointcut("clickMethod() && execution(@mocha.weaving.SingleActionFamily * *(..))")
    public void annotatedClickMethod() {
        Log.d("aop", "annotatedClickMethod");
    }

    @Pointcut("clickMethod() && within(@mocha.weaving.SingleActionFamily *)")
    public void clickMethodInsideAnnotatedType() {
        Log.d("aop", "clickMethodInsideAnnotatedtype");
    }

    @Around("annotatedClickMethod() || clickMethodInsideAnnotatedType()")
    public Object guard(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SingleActionFamily guardClick = signature.getMethod()
                .getAnnotation(SingleActionFamily.class);
        value = guardClick.value();

        if (singleActionFamilies.indexOf(value) == -1) {
            singleActionFamilies.add(value);
        }

        Object result = null;
        if (!HANDLER.hasMessages(singleActionFamilies.indexOf(value))) {
            result = joinPoint.proceed();
            HANDLER.sendEmptyMessageDelayed(singleActionFamilies.indexOf(value), 3000);
        }
        return result;
    }
}
