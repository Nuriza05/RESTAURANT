package peaksoft.dto.responses;

import lombok.Builder;

@Builder
public record AuthResponse(
        String email,
        String token
) {
}
