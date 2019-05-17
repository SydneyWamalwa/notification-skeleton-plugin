package com.matic.gocd.plugins.settings;

import java.util.ArrayList;
import java.util.List;

public class EmptyTopicSettings extends TopicNotificationSettings {
    EmptyTopicSettings() {
        super(null);
    }

    public boolean isEnabled() {
        return false;
    }
    public List<String> endpoints() { return new ArrayList<>(); }
}
