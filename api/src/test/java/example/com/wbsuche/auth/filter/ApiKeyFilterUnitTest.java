package example.com.wbsuche.auth.filter;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import example.com.wbsuche.auth.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyAuthFilterUnitTest {

    private ApiKeyService apiKeyService;
    private ApiKeyFilter filter;

    @BeforeEach
    void setUp() {
        apiKeyService = mock(ApiKeyService.class);
        filter = new ApiKeyFilter(apiKeyService);
    }

    @Test
    void shouldRejectRequestWhenApiKeyIsMissing() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/search");
        when(request.getHeader("X-API-KEY")).thenReturn(null);

        StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        filter.doFilter(request, response, chain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertThat(sw.toString()).contains("Invalid or missing API Key");
        verifyNoInteractions(chain);
    }

    @Test
    void shouldRejectRequestWhenApiKeyIsInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/search");
        when(request.getHeader("X-API-KEY")).thenReturn("invalid-key");
        when(apiKeyService.isValid("invalid-key")).thenReturn(false);

        StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        filter.doFilter(request, response, chain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertThat(sw.toString()).contains("Invalid or missing API Key");
        verifyNoInteractions(chain);
    }

    @Test
    void shouldAllowRequestWhenApiKeyIsValid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/search");
        when(request.getHeader("X-API-KEY")).thenReturn("valid-key");
        when(apiKeyService.isValid("valid-key")).thenReturn(true);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
