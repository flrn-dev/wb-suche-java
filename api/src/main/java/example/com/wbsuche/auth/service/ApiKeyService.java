package example.com.wbsuche.auth.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import example.com.wbsuche.auth.model.ApiKey;

@Service
public class ApiKeyService {

    private final List<ApiKey> validKeys;

    public ApiKeyService(
            @Value("${app.api.key}") String apiKey,
            @Value("${app.api.client}") String client) {
        this.validKeys = Collections.singletonList(new ApiKey(apiKey, client, true));
    }

    public boolean isValid(String apiKey) {
        return validKeys.stream()
                .anyMatch(key -> key.isActive() && key.getKey().equals(apiKey));
    }
}
