package example.com.wbsuche.dictionary.controller;

import com.example.dictionary.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Words", description = "Durchsuchen des Wörterbuchs")
public class DictionaryController {

    private DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Operation(summary = "Suche Wörter", description = "Durchsucht das Wörterbuch mittels Template und optionalem Buchstaben-Filter")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> search(
            @Parameter(description = "Template für das Lemma") @RequestParam(required = false) String template,
            @Parameter(description = "Nur Buchstaben, die im Lemma vorkommen dürfen") @RequestParam(required = false) String letters,
            @Parameter(description = "Seitenzahl (beginnend bei 0)") @RequestParam(defaultValue = "0") long page,
            @Parameter(description = "Seitengröße") @RequestParam(defaultValue = "10") long pageSize,
            @Parameter(description = "Sortierung") @RequestParam(defaultValue = "alphabetical_asc") String sortBy) {

        List<WordEntry> results = dictionaryService.search(template, letters, page, pageSize, sortBy);

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("pageSize", pageSize);
        response.put("sortBy", sortBy);
        response.put("results", results);
        response.put("source",
                "Diese Daten stammen aus der <a href=\"https://www.dwds.de/lemma/list\" target=\"_blank\">DWDS-Lemmadatenbank</a> "
                        + "[Author: Digitales Wörterbuch der deutschen Sprache (DWDS)]. "
                        + "Die Datenbank steht unter der <a href=\"https://creativecommons.org/licenses/by-sa/4.0/deed.de\" target=\"_blank\">"
                        + "Creative Commons BY-SA 4.0</a> Lizenz.");

        return ResponseEntity.ok(response);
    }
}
