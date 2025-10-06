package example.com.wbsuche.auth.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import example.com.wbsuche.auth.service.ApiKeyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiKeyAuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApiKeyService apiKeyService;

    @Test
    void shouldReturnUnauthorizedWithoutApiKey() throws Exception {
        mockMvc.perform(get("/search"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWithInvalidApiKey() throws Exception {
        when(apiKeyService.isValid("wrong-key")).thenReturn(false);

        mockMvc.perform(get("/search")
               .header("X-API-KEY", "wrong-key"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnOkWithValidApiKey() throws Exception {
        when(apiKeyService.isValid("123456")).thenReturn(true);

        mockMvc.perform(get("/search")
               .header("X-API-KEY", "123456"))
               .andExpect(status().isOk());
    }
}
