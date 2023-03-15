package peaksoft.dto.requests;

import lombok.Builder;


@Builder
public record AuthRequest(
        String email,
        String password
) {


}
