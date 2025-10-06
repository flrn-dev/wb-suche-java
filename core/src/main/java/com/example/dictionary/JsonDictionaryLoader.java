package com.example.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonDictionaryLoader {
    
    public Dictionary load(String json) {
        JsonArray arr = Json.parse(json).asArray();

        List<WordEntry> list = new ArrayList<>();
        for (JsonValue val : arr) {
            JsonObject obj = val.asObject();

            List<String> otherLemmata = obj.get("other_lemmata") != null
                    ? obj.get("other_lemmata").asArray().values().stream()
                        .map(JsonValue::asString)
                        .collect(Collectors.toList())
                    : Collections.emptyList();

            JsonValue freqVal = obj.get("freq");
            String freq = null;
            if (freqVal != null) {
                if (freqVal.isString()) {
                    freq = freqVal.asString();
                } else if (freqVal.isNumber()) {
                    freq = String.valueOf(freqVal.asInt());
                }
            }

            WordEntry entry = new WordEntry(
                    obj.getString("date", null),
                    otherLemmata,
                    obj.getString("pos", null),
                    obj.getString("type", null),
                    obj.getString("lemma", null),
                    obj.getString("url", null),
                    freq
            );
            list.add(entry);
        }
        return new Dictionary(list);
    }
}
