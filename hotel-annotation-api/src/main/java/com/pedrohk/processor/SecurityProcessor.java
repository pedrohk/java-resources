package com.pedrohk.processor;

import com.pedrohk.annotation.PremiumOnly;
import java.lang.reflect.Method;

public class SecurityProcessor {

    public static Object process(Object target, String methodName, boolean isVip) throws Exception {
        Method method = target.getClass().getMethod(methodName);

        if (method.isAnnotationPresent(PremiumOnly.class)) {
            if (!isVip) {
                PremiumOnly annotation = method.getAnnotation(PremiumOnly.class);
                throw new SecurityException(annotation.message());
            }
        }

        return method.invoke(target);
    }
}
