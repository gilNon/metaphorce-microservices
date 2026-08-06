package com.book_system.author_service.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Unexpected internal error";

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(GeneralException ex,
                                                                   HttpServletRequest request) {

        log.error("General exception at {}", request.getRequestURI(), ex);
        HttpStatus status = ex.getStatus();

        return buildResponse(status, ex.getMessage(), request, List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex,
                                                            HttpServletRequest request) {

        log.error("Unexpected exception at {}", request.getRequestURI(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return buildResponse(status, INTERNAL_SERVER_ERROR_MESSAGE, request, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                         HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ErrorResponseDto.Detail> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> {
                    if (err instanceof FieldError fieldError) {
                        return new ErrorResponseDto.Detail(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new ErrorResponseDto.Detail(err.getObjectName(), err.getDefaultMessage());
                })
                .toList();
        return buildResponse(status, VALIDATION_FAILED_MESSAGE, request, details);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                             HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String name = ex.getName();
        Class<?> requiredTypeClass = ex.getRequiredType();
        String requiredType = requiredTypeClass == null ? "type" : requiredTypeClass.getSimpleName();

        String message = "Parameter '" + name + "' must be a valid " + requiredType;
        if (ex.getValue() != null) {
            message += " (received: " + ex.getValue() + ")";
        }

        List<ErrorResponseDto.Detail> details = List.of(new ErrorResponseDto.Detail(name, message));

        return buildResponse(status, message, request, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex,
                                                                      HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ErrorResponseDto.Detail> details = ex.getConstraintViolations()
                .stream()
                .map(v -> new ErrorResponseDto.Detail(v.getPropertyPath().toString(), v.getMessage()))
                .toList();

        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        if (message.isBlank()) {
            message = VALIDATION_FAILED_MESSAGE;
        }

        return buildResponse(status, message, request, details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                         HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = "The provided request body contains invalid JSON or unreadable content";
        String fieldPath = null;

        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException mappingException) {
            List<JsonMappingException.Reference> path = mappingException.getPath();
            if (path != null && !path.isEmpty()) {
                fieldPath = jsonPathToString(path);
                message = "The provided value for field '" + fieldPath + "' is not in a valid format";
            }
            if (mappingException instanceof InvalidFormatException invalidFormat) {
                log.error("Invalid format for field {} expected {}", fieldPath, invalidFormat.getTargetType());
            }
        }

        List<ErrorResponseDto.Detail> details = fieldPath == null
                ? List.of()
                : List.of(new ErrorResponseDto.Detail(fieldPath, message));

        return buildResponse(status, message, request, details);
    }

    private String jsonPathToString(List<JsonMappingException.Reference> path) {
        return path.stream()
                .map(ref -> {
                    if (ref.getFieldName() != null) {
                        return ref.getFieldName();
                    }
                    if (ref.getIndex() >= 0) {
                        return "[" + ref.getIndex() + "]";
                    }
                    return "?";
                })
                .collect(Collectors.joining("."))
                .replace(".[", "[");
    }

    private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status,
                                                           String message,
                                                           HttpServletRequest request,
                                                           List<ErrorResponseDto.Detail> details) {
        return ResponseEntity
                .status(status)
                .body(buildErrorResponse(status, message, request, details));
    }

    private ErrorResponseDto buildErrorResponse(HttpStatus status,
                                                String message,
                                                HttpServletRequest request,
                                                List<ErrorResponseDto.Detail> details) {
        return new ErrorResponseDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                Instant.now(),
                details);
    }


}
