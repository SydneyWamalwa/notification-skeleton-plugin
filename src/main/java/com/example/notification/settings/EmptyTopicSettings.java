package com.example.notification.settings;

import com.amihaiemil.eoyaml.YamlMapping;

public class EmptyTopicSettings extends TopicNotificationSettings {
    public EmptyTopicSettings() {
        super(null);
    }

    public boolean isEnabled() {
        return false;
    }
}
