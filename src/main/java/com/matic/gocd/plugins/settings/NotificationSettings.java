package com.matic.gocd.plugins.settings;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;

import java.io.File;
import java.io.IOException;

public class NotificationSettings {
    public final TopicNotificationSettings agent;
    public final TopicNotificationSettings stage;

    private NotificationSettings(YamlMapping mapping) {
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

    public static NotificationSettings forPath(String path) throws IOException {
        File input = new File(path);
        YamlMapping mapping = Yaml.createYamlInput(input).readYamlMapping();
        return new NotificationSettings(mapping);
    }

    public static NotificationSettings defaultSettings() {
        YamlMapping mapping = Yaml.createYamlMappingBuilder().build();
        return new NotificationSettings(mapping);
    }
}
