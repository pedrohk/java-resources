package com.pedrohk.hotel.auth;

import java.lang.reflect.Method;
import java.util.Arrays;

public class SecurityInterceptor {

    public void inspect(Object target, String methodName) throws Exception {
        Method method = Arrays.stream(target.getClass().getDeclaredMethods())
            .filter(m -> m.getName().equals(methodName))
        .findFirst()
            .orElseThrow(() -> new NoSuchMethodException(methodName));

        if (method.isAnnotationPresent(RequiresAuth.class)) {
                RequiresAuth annotation = method.getAnnotation(RequiresAuth.class);
                String requiredRole = annotation.role();
                String currentRole = SecurityContext.getRole();

                if (currentRole == null || (!currentRole.equals(requiredRole) && !currentRole.equals("ADMIN"))) {
                    throw new SecurityException("Access Denied: Required role " + requiredRole);
                }
            }
    }
}

