package com.danclowry.noteapp.database;

import java.util.prefs.Preferences;

public class DatabaseConfig {
    private static Preferences prefs = Preferences.userRoot().node("com/danclowry/noteapp/connection");
    private static final String HOSTNAME = "hostname";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static String getHostname() {
        return prefs.get(HOSTNAME, "localhost");
    }

    public static void setHostname(String newValue) {
        prefs.put(HOSTNAME, newValue);
    }

    public static void resetHostname() {
        prefs.remove(HOSTNAME);
    }


    public static String getUsername() {
        return prefs.get(USERNAME, "root");
    }

    public static void setUsername(String newValue) {
        prefs.put(USERNAME, newValue);
    }

    public static void resetUsername() {
        prefs.remove(USERNAME);
    }


    public static String getPassword() {
        return prefs.get(PASSWORD, null);
    }

    public static void setPassword(String newValue) {
        prefs.put(PASSWORD, newValue);
    }

    public static void resetPassword() {
        prefs.remove(PASSWORD);
    }
}
