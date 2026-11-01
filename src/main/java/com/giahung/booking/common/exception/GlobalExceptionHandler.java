package com.giahung.booking.common.exception;

import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String path, List<FieldErrorDetail> errors){
        ErrorResponse errorResponse = new ErrorResponse(status.value(),status.getReasonPhrase(), message, path,Instant.now(), errors);
        return ResponseEntity.status(status).body(errorResponse);
    }


    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exc,HttpServletRequest request){
        return buildResponse(HttpStatus.NOT_FOUND,exc.getMessage(),request.getRequestURI(), null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException exc, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT,exc.getMessage(),request.getRequestURI(), null);
    }

    // @Valid trên @RequestBody fail: trả danh sách lỗi theo từng field
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exc, HttpServletRequest request) {
        List<FieldErrorDetail> fieldErrors = exc.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        
        return buildResponse(HttpStatus.BAD_REQUEST,"Validation failed",request.getRequestURI(),fieldErrors);
    }

    // Body JSON hỏng, sai kiểu dữ liệu hoặc giá trị enum không tồn tại
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(HttpMessageNotReadableException exc, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST,"Malformed JSON request",request.getRequestURI(), null);
    }

    // Tham số URL sai kiểu, ví dụ /api/events/abc khi mong đợi số
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exc, HttpServletRequest request) {
        String message = "Parameter '" + exc.getName() + "' has invalid value";
        return buildResponse(HttpStatus.BAD_REQUEST,message,request.getRequestURI(),null);
    }

    // Catch-all: log đầy đủ ở server, client chỉ nhận thông báo chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exc, HttpServletRequest request) {
        log.error("Unhandled exception at {}", request.getRequestURI(), exc);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error",request.getRequestURI(), null);
    }
}
