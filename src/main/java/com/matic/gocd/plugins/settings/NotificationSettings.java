package com.matic.gocd.plugins.settings;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.matic.gocd.plugins.utils.Util;

import java.io.File;
import java.io.IOException;

public class NotificationSettings {
    public final TopicNotificationSettings agent;
    public final TopicNotificationSettings stage;

    public NotificationSettings(YamlMapping mapping) {
        this.agent = parseSettings(mapping, "agent_status");
        this.stage = parseSettings(mapping, "stage_status");
    }

    private TopicNotificationSettings parseSettings(YamlMapping mapping, String topic) {
        try {
            return new TopicNotificationSettings(mapping.yamlMapping(topic));
        } catch (IllegalStateException e) {
            return new EmptyTopicSettings();
        }
    }

    public static NotificationSettings forPath(String path) {
        try {
            File input = new File(path);
            YamlMapping mapping = Yaml.createYamlInput(input).readYamlMapping();
            return new NotificationSettings(mapping);
        } catch (IOException | NullPointerException e) {
//            e.printStackTrace();
            return forPath(Util.getFilePath("/settings.yml"));
        }
    }

    public static NotificationSettings fromEnvironmentVariable() {
        String path = System.getenv("GOCD_HTTP_NOTIFICATIONS_CONFIG");
        return forPath(path);
    }


    public boolean isStageStatusEnabled() {
        return false;
    }
}
