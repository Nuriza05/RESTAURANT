package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record SubcategoryRequest(
        @Positive(message = "category id should be positive number!")
        @NotNull(message = "category id shouldn't be null!")
        Long categoryId,
        @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String name
) {
}
