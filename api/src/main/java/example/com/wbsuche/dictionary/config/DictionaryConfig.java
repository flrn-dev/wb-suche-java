package example.com.wbsuche.dictionary.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.example.dictionary.DictionaryService;
import com.example.dictionary.JsonDictionaryLoader;

@Configuration
public class DictionaryConfig {

    @Bean
    public DictionaryService dictionaryService() throws IOException {
        JsonDictionaryLoader loader = new JsonDictionaryLoader();
        ClassPathResource resource = new ClassPathResource("dict.json");
        InputStream in = resource.getInputStream();
        String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        return new DictionaryService(loader, json);
    }
}
