package com.flora.week9taskblog.exceptionHandler;

import com.flora.week9taskblog.Payload.Response.ErrorMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity handleJDBCUpdateException(DataAccessException ex){
        String errorMsgDesc = ex.getLocalizedMessage();

        if (errorMsgDesc == null) errorMsgDesc = "cannot perform an update operation";

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMsgDesc);

        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataAccessException.class, IncorrectResultSizeDataAccessException.class})
    public ResponseEntity handleJDBCQueryForObjectException(Exception ex){
        String errorMsgDesc = ex.getLocalizedMessage();
        if (errorMsgDesc == null) errorMsgDesc = "cannot perform an queryForObject operation";
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMsgDesc);
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
