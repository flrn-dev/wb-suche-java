package example.com.wbsuche.dictionary.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import example.com.wbsuche.dictionary.controller.DictionaryController;

@ControllerAdvice(assignableTypes = DictionaryController.class)
public class DictionaryExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", ex.getMessage()));
    }
}
