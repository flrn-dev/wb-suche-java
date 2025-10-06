package example.com.wbsuche.dictionary.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dictionary.DictionaryService;
import com.example.dictionary.WordEntry;

class DictionaryControllerUnitTest {

    private DictionaryService dictionaryService;
    private DictionaryController dictionaryController;

    @BeforeEach
    void setUp() {
        dictionaryService = mock(DictionaryService.class);
        dictionaryController = new DictionaryController(dictionaryService);
    }

    @Test
    void testSearchReturnsExpectedResults() {
        WordEntry apfel = new WordEntry();
        apfel.setLemma("Apfel");

        when(dictionaryService.search("Apfel", "Apfel", 0, 10, "alphabetical_asc"))
                .thenReturn(Arrays.asList(apfel));

        ResponseEntity<Map<String, Object>> response = dictionaryController.search("Apfel", "Apfel", 0, 10,
                "alphabetical_asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();

        assertEquals(1, ((List<?>) body.get("results")).size());
        assertEquals("Apfel", ((WordEntry) ((List<?>) body.get("results")).get(0)).getLemma());
    }

    @Test
    void testSearchEmptyResults() {
        when(dictionaryService.search("Banane", "Banane", 0, 10, "alphabetical_asc"))
                .thenReturn(Arrays.asList());

        ResponseEntity<Map<String, Object>> response = dictionaryController.search("Banane", "Banane", 0, 10,
                "alphabetical_asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();

        assertEquals(0, ((List<?>) body.get("results")).size());
    }
}
