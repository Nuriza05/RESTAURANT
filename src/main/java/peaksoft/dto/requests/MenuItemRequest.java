package peaksoft.dto.requests;

import lombok.Builder;
import lombok.Data;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record MenuItemRequest(
        Long restaurantId,
        Long subcategoryId,
        String name,
        String image,
        int price,
        String description,
        Boolean isVegetarian


) {
}
