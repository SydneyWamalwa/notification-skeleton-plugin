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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matic.gocd.plugins.RequestExecutor;
import com.matic.gocd.plugins.settings.TopicNotificationSettings;
import com.matic.gocd.plugins.utils.WebhookSender;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Collections;
import java.util.HashMap;

public class TopicStatusRequestExecutor implements RequestExecutor {
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private static final Logger LOG = Logger.getLoggerFor(TopicStatusRequestExecutor.class);

    private final String requestBody;
    private final TopicNotificationSettings settings;
    private final WebhookSender sender;

    public TopicStatusRequestExecutor(TopicNotificationSettings settings, String requestBody) {
        this(settings, requestBody, new WebhookSender());
    }

    public TopicStatusRequestExecutor(TopicNotificationSettings settings, String requestBody, WebhookSender sender) {
        this.settings = settings;
        this.requestBody = requestBody;
        this.sender = sender;
    }

    @Override
    public GoPluginApiResponse execute() {
        HashMap<String, Object> responseJson = new HashMap<>();
        try {
            sendNotification();
            responseJson.put("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.put("status", "failure");
            responseJson.put("messages", Collections.singletonList(e.getMessage()));
        }
        return new DefaultGoPluginApiResponse(200, GSON.toJson(responseJson));
    }

    private void sendNotification() {
        try{
            if(settings==null){
                LOG.error("Notification settings is null!");
                return;
            }

        if (settings.isEnabled()) {
            for (String endpoint : settings.endpoints()) {
                LOG.info("Sending notification to: " + endpoint);
                sender.send(endpoint, requestBody);
            }
            LOG.info("Notification sent successfully.");
        } else {
            LOG.info("Notification Sending disabled");
        }
    }catch (Exception e){
        LOG.error("Failed to send Notification: " + e.getMessage(), e);
        }
    }

}
