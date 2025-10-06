package example.com.wbsuche.dictionary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DictionaryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String API_KEY = "123456";
    private static final String SEARCH_ENDPOINT = "/search";

    @Test
    void testSearchEndpoint() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", API_KEY);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromPath(SEARCH_ENDPOINT)
                .queryParam("template", "A___l")
                .queryParam("letters", "ap?f?el")
                .queryParam("page", 0)
                .queryParam("pageSize", 10)
                .queryParam("sortBy", "alphabetical_asc")
                .build()
                .toUri();

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body, "Response body must not be null");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> results = (List<Map<String, Object>>) body.get("results");
        assertNotNull(results, "Results must not be null");

        assertTrue(results.stream()
                .map(e -> ((String) e.get("lemma")).toLowerCase())
                .anyMatch(lemma -> lemma.startsWith("a")),
                "At least one lemma must start with 'a'");

        List<String> lemmata = results.stream()
                .map(e -> ((String) e.get("lemma")).toLowerCase())
                .toList();

        List<String> sorted = new ArrayList<>(lemmata);
        Collections.sort(sorted);

        assertEquals(sorted, lemmata, "Results must be sorted alphabetically ascending");
    }
}
