package com.danclowry.noteapp.database;

import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConfigTest {

    private static String prefsPath = "com/danclowry/noteapp/connection";

    @Test
    public void getHostname() {
        Preferences prefs = Preferences.userRoot().node(prefsPath);
        assertEquals(prefs.get("hostname", "localhost"), DatabaseConfig.getHostname());
    }

    @Test
    public void setHostname() {
        String currentValue = DatabaseConfig.getHostname();
        DatabaseConfig.resetHostname();
        DatabaseConfig.setHostname("testvalue");
        assertEquals("testvalue", DatabaseConfig.getHostname());
        DatabaseConfig.setHostname(currentValue);
    }

    @Test
    public void resetHostname() {
        String currentValue = DatabaseConfig.getHostname();
        DatabaseConfig.setHostname("testvalue");
        DatabaseConfig.resetHostname();
        assertEquals("localhost", DatabaseConfig.getHostname());
        DatabaseConfig.setHostname(currentValue);
    }

    @Test
    public void getUsername() {
        Preferences prefs = Preferences.userRoot().node(prefsPath);
        assertEquals(prefs.get("username", "root"), DatabaseConfig.getUsername());
    }

    @Test
    public void setUsername() {
        String currentValue = DatabaseConfig.getUsername();
        DatabaseConfig.resetUsername();
        DatabaseConfig.setUsername("testvalue");
        assertEquals("testvalue", DatabaseConfig.getUsername());
        DatabaseConfig.setUsername(currentValue);
    }

    @Test
    public void resetUsername() {
        String currentValue = DatabaseConfig.getUsername();
        DatabaseConfig.setUsername("testvalue");
        DatabaseConfig.resetUsername();
        assertEquals("root", DatabaseConfig.getUsername());
        DatabaseConfig.setUsername(currentValue);
    }

    @Test
    public void getPassword() {
        Preferences prefs = Preferences.userRoot().node(prefsPath);
        assertEquals(prefs.get("password", null), DatabaseConfig.getPassword());
    }

    @Test
    public void setPassword() {
        String currentValue = DatabaseConfig.getPassword();
        DatabaseConfig.resetPassword();
        DatabaseConfig.setPassword("testvalue");
        assertEquals("testvalue", DatabaseConfig.getPassword());
        if (currentValue != null && !currentValue.equals("testvalue")) {
            DatabaseConfig.setPassword(currentValue);
        } else {
            DatabaseConfig.resetPassword();
        }
    }

    @Test
    public void resetPassword() {
        String currentValue = DatabaseConfig.getPassword();
        DatabaseConfig.setPassword("testvalue");
        DatabaseConfig.resetPassword();
        assertNull(DatabaseConfig.getPassword());
        if (currentValue != null && !currentValue.equals("testvalue")) {
            DatabaseConfig.setPassword(currentValue);
        } else {
            DatabaseConfig.resetPassword();
        }
    }
}
