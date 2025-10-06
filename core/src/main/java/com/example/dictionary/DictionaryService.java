package com.example.dictionary;

import java.util.List;

public class DictionaryService {

    private Dictionary dictionary;

    public DictionaryService(JsonDictionaryLoader loader, String json) {
        this.dictionary = loader.load(json);
    }

    public Dictionary getDictionary() {
        if (dictionary == null) {
            throw new IllegalStateException("Dictionary not initialized");
        }
        return dictionary;
    }

    public List<WordEntry> search(String template, String letters, long page, long pageSize, String sortBy) {
        return getDictionary().search(template, letters, page, pageSize, sortBy);
    }
}
