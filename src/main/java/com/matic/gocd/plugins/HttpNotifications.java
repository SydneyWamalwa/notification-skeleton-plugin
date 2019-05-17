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

package com.matic.gocd.plugins;

import com.matic.gocd.plugins.executors.*;
import com.matic.gocd.plugins.settings.NotificationSettings;
import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.io.IOException;

import static com.matic.gocd.plugins.Constants.PLUGIN_IDENTIFIER;

@Extension
public class HttpNotifications implements GoPlugin {

    private static final Logger LOG = Logger.getLoggerFor(HttpNotifications.class);
    private static final String SETTINGS_ENV = "GOCD_HTTP_NOTIFICATIONS_CONFIG";

    private GoApplicationAccessor accessor;
    private PluginRequest pluginRequest;
    private NotificationSettings settings;

    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor accessor) {
        readSettings();
        this.accessor = accessor;
        this.pluginRequest = new PluginRequest(accessor);
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest request) throws UnhandledRequestTypeException {
        try {
            switch (Request.fromString(request.requestName())) {
                case PLUGIN_SETTINGS_GET_VIEW:
                    return new GetViewRequestExecutor().execute();
                case REQUEST_NOTIFICATIONS_INTERESTED_IN:
                    return new NotificationInterestedInExecutor().execute();
                case REQUEST_STAGE_STATUS:
                    return new TopicStatusRequestExecutor(settings.stage, request.requestBody()).execute();
                case REQUEST_AGENT_STATUS:
                    return new TopicStatusRequestExecutor(settings.agent, request.requestBody()).execute();
                case PLUGIN_SETTINGS_GET_CONFIGURATION:
                    return new GetPluginConfigurationExecutor().execute();
                case PLUGIN_SETTINGS_VALIDATE_CONFIGURATION:
                    return new ValidateConfigurationExecutor().execute();
                default:
                    throw new UnhandledRequestTypeException(request.requestName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return PLUGIN_IDENTIFIER;
    }

    private void readSettings() {
        try {
            String path = System.getenv(SETTINGS_ENV);
            this.settings = NotificationSettings.forPath(path);
        } catch (IOException e) {
            LOG.error("Can read settings from ENV("+SETTINGS_ENV+")", e);
        } finally {
            this.settings = NotificationSettings.defaultSettings();
        }
    }
}
