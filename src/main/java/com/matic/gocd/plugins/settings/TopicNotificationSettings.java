package com.matic.gocd.plugins.settings;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

import java.util.ArrayList;

public class TopicNotificationSettings {
    private final YamlMapping mapping;

    public TopicNotificationSettings(YamlMapping mapping) {
        this.mapping = mapping;
    }

    public boolean isEnabled() {
        return Boolean.valueOf(mapping.string("enabled"));
    }

    public String[] endpoints() {
        YamlSequence sequence = mapping.yamlSequence("endpoints");
        return arrayOfStrings(sequence);
    }

    private String[] arrayOfStrings(YamlSequence sequence) {
        String[] strings = new String[sequence.size()];
        for (int i = 0; i < sequence.size(); i++) {
            strings[i] = sequence.string(i);
        }
        return strings;
    }
}