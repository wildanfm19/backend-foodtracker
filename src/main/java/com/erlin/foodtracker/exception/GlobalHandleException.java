package com.erlin.foodtracker.exception;

import com.erlin.foodtracker.Dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalHandleException extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String , String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error ) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception ,
                                                                            WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                        .apiPath(webRequest.getDescription(false))
                                .errorCode(HttpStatus.NOT_FOUND)
                                        .errorMessage(exception.getMessage())
                                                .errorTime(LocalDateTime.now())
                                                        .build();
        return new ResponseEntity<>(errorResponseDTO , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleApiException(ApiException exception , WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                        .apiPath(webRequest.getDescription(false))
                                .errorCode(HttpStatus.BAD_REQUEST)
                                        .errorMessage(exception.getMessage())
                                                .errorTime(LocalDateTime.now())
                                                        .build();
        return new ResponseEntity<>(errorResponseDTO , HttpStatus.BAD_REQUEST);
    }
}
