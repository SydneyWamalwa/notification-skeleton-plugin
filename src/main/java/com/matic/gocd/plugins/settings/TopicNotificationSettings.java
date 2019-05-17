package com.matic.gocd.plugins.settings;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

import java.util.ArrayList;
import java.util.List;

public class TopicNotificationSettings {
    private final YamlMapping mapping;

    TopicNotificationSettings(YamlMapping mapping) {
        if (mapping == null) {
            mapping = Yaml.createYamlMappingBuilder().build();
        }
        this.mapping = mapping;
    }

    public boolean isEnabled() {
        return Boolean.valueOf(mapping.string("enabled"));
    }

    public List<String> endpoints() {
        YamlSequence sequence = mapping.yamlSequence("endpoints");
        if (sequence == null) {
            return new ArrayList<>();
        }
        return parseSequence(sequence);
    }

    private List<String> parseSequence(YamlSequence sequence) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < sequence.size(); i++) {
            strings.add(sequence.string(i));
        }
        return strings;
    }
}