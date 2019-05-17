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
import com.matic.gocd.plugins.settings.NotificationSettings;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class StageStatusRequestExecutor implements RequestExecutor {
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private final String requestBody;
    private final NotificationSettings settings;

    public StageStatusRequestExecutor(NotificationSettings settings, String requestBody) {
        this.settings = settings;
        this.requestBody = requestBody;
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

    protected void sendNotification() throws Exception {
        if (settings.stage.isEnabled()) {
            System.out.println("eeeee");
            for (String endpoint : settings.stage.endpoints()) {
                sendHttpNotification(endpoint);
            }
        }
    }

    private void sendHttpNotification(String endpoint) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringEntity requestEntity = new StringEntity(
                requestBody,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(endpoint);
        postMethod.setEntity(requestEntity);

        try {
            httpClient.execute(postMethod);
        } finally {
            httpClient.close();
        }
    }
}
