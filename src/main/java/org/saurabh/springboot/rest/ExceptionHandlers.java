package org.saurabh.springboot.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlers {

    private MessageSource messageSource;

    @Autowired
    public ExceptionHandlers(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessages handleException(MethodArgumentNotValidException ex) {
        Map<String, LocalizedFieldErrorMessage> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> new LocalizedFieldErrorMessage(fieldError, messageSource)));

        return new ErrorMessages(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessages handleException(HttpMessageNotReadableException ex) {
        Matcher matcher = Pattern.compile(".*org\\.saurabh\\.springboot\\.domain\\.(\\w+).*").matcher(ex.getMessage().split("\n")[0]);
        if (matcher.matches()) {
            HashMap<String, LocalizedFieldErrorMessage> errors = new HashMap<>();
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String invalidFieldName = matcher.group(i).toLowerCase();
                errors.put(invalidFieldName, new LocalizedFieldErrorMessage("invalid_" + invalidFieldName, ex.getMessage()));
            }
            return new ErrorMessages(errors);
        }
        return new ErrorMessages(new HashMap<String, LocalizedFieldErrorMessage>() {{
            put("unknown", new LocalizedFieldErrorMessage("unknown_error", ""));
        }});
    }

    public static class LocalizedFieldErrorMessage {

        @JsonProperty
        private final String code;
        @JsonProperty
        private final String message;

        public LocalizedFieldErrorMessage(FieldError fieldError, MessageSource messageSource) {
            this(fieldError.getDefaultMessage(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
        }

        public LocalizedFieldErrorMessage(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    public static class ErrorMessages {
        @JsonProperty
        private Map<String, LocalizedFieldErrorMessage> errors;

        public ErrorMessages(Map<String, LocalizedFieldErrorMessage> errorMessages) {
            this.errors = errorMessages;
        }
    }
}
