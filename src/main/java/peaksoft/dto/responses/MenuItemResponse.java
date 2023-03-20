package peaksoft.dto.responses;

import lombok.Builder;
import peaksoft.entity.Subcategory;

@Builder
public record MenuItemResponse(
        String categoryName,
        Long id,
        String name,
        String image,
        int price,
        String description,
        boolean IsVegetarian
) {
}
