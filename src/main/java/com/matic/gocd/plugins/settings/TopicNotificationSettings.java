package com.matic.gocd.plugins.settings;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

import java.util.ArrayList;
import java.util.List;

public class TopicNotificationSettings {
    private boolean enabled;
    private List<String> endpoints;

    TopicNotificationSettings() {
        this.enabled = false;
        this.endpoints = new ArrayList<>();
    }

    TopicNotificationSettings(YamlMapping mapping) {
        this();
        if (mapping != null) {
            parseSettings(mapping);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> endpoints() { return endpoints; }

    private void parseSettings(YamlMapping mapping) {
        this.enabled = Boolean.valueOf(mapping.string("enabled"));
        this.endpoints = parseSequence(mapping.yamlSequence("endpoints"));
    }

    private List<String> parseSequence(YamlSequence sequence) {
        ArrayList<String> strings = new ArrayList<>();
        if (sequence != null) {
            for (int i = 0; i < sequence.size(); i++) {
                strings.add(sequence.string(i));
            }
        }
        return strings;
    }
}