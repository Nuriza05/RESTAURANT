package peaksoft.dto.requests;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record MenuItemRequest(
        @NotNull(message = "restaurant id shouldn't be null!")
        @Positive(message = "restaurant id should be positive number!")
        Long restaurantId,
        @NotNull(message = "subcategory id shouldn't be null!")
        @Positive(message = "subcategory id should be positive number!")
        Long subcategoryId,
        @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String name,
        @NotNull(message = "Image shouldn't be null!")
        String image,
        @NotNull(message = "price shouldn't be null!")
        @Positive(message = "price should be positive number!")
        int price,
        @NotNull(message = "description shouldn't be null!")
        String description,
        @NotNull(message = "isVegetarian shouldn't be null!")
        Boolean isVegetarian


) {
}
