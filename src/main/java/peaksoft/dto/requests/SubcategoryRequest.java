package peaksoft.dto.requests;

import lombok.Builder;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record SubcategoryRequest(
        Long categoryId,
        String name
) {
}
