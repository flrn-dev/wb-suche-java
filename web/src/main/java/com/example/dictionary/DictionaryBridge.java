package com.example.dictionary;

import org.teavm.jso.JSExport;
import org.teavm.jso.JSObject;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

public class DictionaryBridge implements JSObject {

    private static DictionaryService dictionaryService;

    @JSExport
    public static void init(String json) {
        JsonDictionaryLoader loader = new JsonDictionaryLoader();
        dictionaryService = new DictionaryService(loader, json);
    }

    @JSExport
    public static String search(String template, String letters, long page, long pageSize, String sortBy) {
        var results = dictionaryService.search(template, letters, page, pageSize, sortBy);

        JsonArray arr = new JsonArray();
        for (WordEntry w : results) {
            JsonObject obj = new JsonObject()
                    .add("date", w.getDate())
                    .add("pos", w.getPos())
                    .add("type", w.getType())
                    .add("lemma", w.getLemma())
                    .add("url", w.getUrl())
                    .add("freq", w.getFreq());

            JsonArray other = new JsonArray();
            for (String o : w.getOther_lemmata()) {
                other.add(o);
            }
            obj.add("other_lemmata", other);

            arr.add(obj);
        }
        return arr.toString();
    }

    public static void main(String[] args) {
    }
}
