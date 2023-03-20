package peaksoft.dto.responses;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String name
) {
}
