package com.randycasburn.thing;

import com.randycasburn.thing.integration.ThingDatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerErrorException;
import org.slf4j.Logger;

@ControllerAdvice
public class RestExceptionHandler {
    @Autowired
    Logger logger;
    private static final String DB_ERROR_MSG =
            "Error communicating with the database";

    @ExceptionHandler(ThingDatabaseException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ServerErrorException handleResourceNotFound(final ThingDatabaseException exception,
                                                                     final HttpServletRequest request) {
        logger.error(DB_ERROR_MSG);
        return new ServerErrorException(exception.getMessage(), exception);
    }

}
