/*
 * Copyright 2018 ThoughtWorks, Inc.
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

package com.matic.gocd.plugins.executors;

import com.matic.gocd.plugins.settings.TopicNotificationSettings;
import com.matic.gocd.plugins.utils.WebhookSender;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class TopicStatusRequestExecutorTest {

    private static final String ENDPOINT = "https:/matic.com/test";
    private static final String PAYLOAD = "{payload: true}";

    @Test
    public void shouldRenderASuccessResponseIfNotificationWasSent() {
        TopicNotificationSettings settings = getTopicNotificationSettings();
        WebhookSender sender = mock(WebhookSender.class);
        GoPluginApiResponse response = new TopicStatusRequestExecutor(settings, PAYLOAD, sender).execute();

        assertThat(response.responseCode(), is(200));
        JSONAssert.assertEquals("{\"status\":\"success\"}", response.responseBody(), true);
    }

    @Test
    public void shouldSendNotificationIfEnabled() {
        TopicNotificationSettings settings = getTopicNotificationSettings();
        WebhookSender sender = mock(WebhookSender.class);
        new TopicStatusRequestExecutor(settings, PAYLOAD, sender).execute();

        verify(sender).send(ENDPOINT, PAYLOAD);
    }

    @Test
    public void shouldRenderASuccessResponseIfNotificationsDisabled() {
        TopicNotificationSettings settings = getTopicNotificationSettings();
        when(settings.isEnabled()).thenReturn(false);
        WebhookSender sender = mock(WebhookSender.class);
        GoPluginApiResponse response = new TopicStatusRequestExecutor(settings, "{}", sender).execute();

        assertThat(response.responseCode(), is(200));
        JSONAssert.assertEquals("{\"status\":\"success\"}", response.responseBody(), true);
    }

    @Test
    public void shouldNotSendNotificationIfDisabled() {
        TopicNotificationSettings settings = getTopicNotificationSettings();
        WebhookSender sender = mock(WebhookSender.class);
        when(settings.isEnabled()).thenReturn(false);
        new TopicStatusRequestExecutor(settings, PAYLOAD, sender).execute();

        verifyNoMoreInteractions(sender);
    }


    private TopicNotificationSettings getTopicNotificationSettings() {
        TopicNotificationSettings settings = mock(TopicNotificationSettings.class);
        when(settings.isEnabled()).thenReturn(true);
        when(settings.endpoints()).thenReturn(Collections.singletonList(ENDPOINT));
        return settings;
    }
}
