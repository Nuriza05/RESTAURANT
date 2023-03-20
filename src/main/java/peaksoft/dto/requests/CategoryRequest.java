package peaksoft.dto.requests;

import lombok.Builder;

@Builder
public record CategoryRequest(
        String name
) {
}
