package peaksoft.dto.responses;

import lombok.Builder;
import peaksoft.entity.Category;

@Builder
public record SubcategoryResponse(
        String categoryName,
        Long id,
        String name
) {
}
