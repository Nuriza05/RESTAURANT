package peaksoft.dto.responses;

import lombok.Builder;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record UserTokenResponse(
        String email,
        String token
) {
}
