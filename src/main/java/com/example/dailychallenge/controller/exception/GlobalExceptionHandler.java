package com.example.dailychallenge.controller.exception;

import com.example.dailychallenge.exception.FileNotUpload;
import com.example.dailychallenge.exception.challenge.ChallengeCategoryNotFound;
import com.example.dailychallenge.exception.users.UserDuplicateNotCheck;
import com.example.dailychallenge.exception.users.UserDuplicateCheck;
import com.example.dailychallenge.exception.users.UserLoginFailure;
import com.example.dailychallenge.exception.users.UserPasswordCheck;
import com.example.dailychallenge.vo.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChallengeCategoryNotFound.class)
    protected ResponseEntity<ResponseError> handlerChallengeCategoryNotFound(ChallengeCategoryNotFound challengeCategoryNotFound) {
        final ResponseError responseError = ResponseError.builder()
                .code(challengeCategoryNotFound.getStatusCode())
                .message(challengeCategoryNotFound.getMessage())
                .build();

        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(FileNotUpload.class)
    protected ResponseEntity<ResponseError> handlerFileNotUpload(FileNotUpload fileNotUpload) {
        final ResponseError responseError = ResponseError.builder()
                .code(fileNotUpload.getStatusCode())
                .message(fileNotUpload.getMessage())
                .build();

        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(UserDuplicateCheck.class)
    protected ResponseEntity<ResponseError> handlerUserIdDuplicate(UserDuplicateCheck userDuplicateCheck) {
        final ResponseError responseError = ResponseError.builder()
                .code(userDuplicateCheck.getStatusCode())
                .message(userDuplicateCheck.getMessage())
                .build();

        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(UserDuplicateNotCheck.class)
    protected ResponseEntity<ResponseError> handlerUserDuplicateNotCheck(UserDuplicateNotCheck userDuplicateNotCheck) {
        final ResponseError responseError = ResponseError.builder()
                .code(userDuplicateNotCheck.getStatusCode())
                .message(userDuplicateNotCheck.getMessage())
                .build();

        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(UserLoginFailure.class)
    protected ResponseEntity<ResponseError> handlerUserLoginFailure(UserLoginFailure userLoginFailure) {
        final ResponseError responseError = ResponseError.builder()
                .code(userLoginFailure.getStatusCode())
                .message(userLoginFailure.getMessage())
                .build();

        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(UserPasswordCheck.class)
    protected ResponseEntity<ResponseError> handlerUserPasswordCheck(UserPasswordCheck userPasswordCheck) {
        final ResponseError responseError = ResponseError.builder()
                .code(userPasswordCheck.getStatusCode())
                .message(userPasswordCheck.getMessage())
                .build();

        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)  // 유효성 검증에 대한 예외처리
    public ResponseEntity<ResponseError> processValidationError(MethodArgumentNotValidException ex) {
        final ResponseError responseError = ResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

}
