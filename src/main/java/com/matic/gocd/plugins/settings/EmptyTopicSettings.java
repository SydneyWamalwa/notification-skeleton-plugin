package com.matic.gocd.plugins.settings;

public class EmptyTopicSettings extends TopicNotificationSettings {
    public EmptyTopicSettings() {
        super(null);
    }

    public boolean isEnabled() {
        return false;
    }
}
