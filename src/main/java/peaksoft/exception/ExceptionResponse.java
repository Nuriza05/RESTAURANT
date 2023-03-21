package peaksoft.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
@Builder
public record ExceptionResponse(
        HttpStatus httpStatus,
        String exceptionClassName,
        String message
) {


}
