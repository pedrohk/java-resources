package com.pedrohk.hotel.auth;

public class SecurityContext {
    private static final ThreadLocal<String> currentUserRole = new ThreadLocal<>();

    public static void setRole(String role) { currentUserRole.set(role); }
    public static String getRole() { return currentUserRole.get(); }
    public static void clear() { currentUserRole.remove(); }
}
