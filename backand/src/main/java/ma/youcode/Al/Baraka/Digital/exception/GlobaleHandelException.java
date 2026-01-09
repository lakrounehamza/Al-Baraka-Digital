package ma.youcode.Al.Baraka.Digital.exception;

import ma.youcode.Al.Baraka.Digital.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobaleHandelException {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse>  DuplicateUserHandel(DuplicateUserException e){
        ErrorResponse   error = new ErrorResponse(409,e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(409).body(error);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse>  NotFoundHandel(NotFoundException e){
        ErrorResponse   error = new ErrorResponse(404,e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(404).body(error);
    }
    @ExceptionHandler(InvalideInputException.class)
    public ResponseEntity<ErrorResponse>  IllegalArgumentHandel(InvalideInputException e){
        ErrorResponse   error = new ErrorResponse(400,e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(400).body(error);
    }
}
