package com.matic.gocd.plugins.settings;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class NotificationSettingsTest {
    @Test
    public void testHandleMissingFile() {
        NotificationSettings settings = NotificationSettings.forPath("blabkabkbas");
        assertNotNull(settings);
        assertFalse(settings.agent.isEnabled());
        assertFalse(settings.isStageStatusEnabled());
    }

    @Test
    public void testInvalidYaml() {
        URL url = this.getClass().getResource("/invalid.txt");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertFalse(settings.agent.isEnabled());
        assertFalse(settings.isStageStatusEnabled());
    }

    @Test
    public void testAgentStatusEnabled() {
        URL url = this.getClass().getResource("/agent-status-enabled.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertTrue(settings.agent.isEnabled());
        assertFalse(settings.isStageStatusEnabled());
    }

    @Test
    public void testAgentStatusEndpoints() {
        URL url = this.getClass().getResource("/agent-status-enabled.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertTrue(settings.agent.isEnabled());
        String[] endpoints = new String[]{"http://agent.status/endpoint1", "http://agent.status/endpoint2"};
        assertArrayEquals(endpoints, settings.agent.endpoints());
    }

}