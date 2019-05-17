package com.matic.gocd.plugins.settings;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NotificationSettingsTest {

    private static final List<String> EMPTY_ENDPOINTS = new ArrayList<>();
    private static final List<String> TEST_ENDPOINTS = Arrays.asList("http://agent.status/endpoint1", "http://agent.status/endpoint2");

    @Test(expected = IOException.class)
    public void testHandleMissingFile() throws IOException {
        NotificationSettings settings = NotificationSettings.forPath("blabkabkbas");
        assertNull(settings);
    }

    @Test
    public void testInvalidYaml() throws IOException {
        URL url = this.getClass().getResource("/invalid.txt");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertFalse(settings.agent.isEnabled());
        assertFalse(settings.stage.isEnabled());
    }

    @Test
    public void testEmptyYaml() throws IOException {
        URL url = this.getClass().getResource("/settings-empty.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertNotNull(settings.agent);
        assertNotNull(settings.stage);
        assertFalse(settings.agent.isEnabled());
        assertFalse(settings.stage.isEnabled());
        assertEquals(EMPTY_ENDPOINTS, settings.agent.endpoints());
        assertEquals(EMPTY_ENDPOINTS, settings.stage.endpoints());
    }

    @Test
    public void testAgentStatusEnabled() throws IOException {
        URL url = this.getClass().getResource("/settings-agent-status-enabled.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertTrue(settings.agent.isEnabled());
        assertFalse(settings.stage.isEnabled());
    }

    @Test
    public void testAgentStatusEndpoints() throws IOException {
        URL url = this.getClass().getResource("/settings-agent-status-enabled.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertEquals(TEST_ENDPOINTS, settings.agent.endpoints());
    }

    @Test
    public void testStageStatusEnabled() throws IOException {
        URL url = this.getClass().getResource("/settings-stage-status-enabled.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertTrue(settings.stage.isEnabled());
        assertFalse(settings.agent.isEnabled());
    }

    @Test
    public void testStageStatusEndpoints() throws IOException {
        URL url = this.getClass().getResource("/settings-stage-status-enabled.yml");
        NotificationSettings settings = NotificationSettings.forPath(url.getFile());
        assertNotNull(settings);
        assertEquals(TEST_ENDPOINTS, settings.stage.endpoints());
    }
}