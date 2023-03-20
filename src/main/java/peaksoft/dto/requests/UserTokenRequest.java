package peaksoft.dto.requests;

import lombok.Builder;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record UserTokenRequest( String email,
                                String password) {
}
