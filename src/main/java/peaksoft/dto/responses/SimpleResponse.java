package peaksoft.dto.responses;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record SimpleResponse(
        HttpStatus status,
        String message
) {
}
