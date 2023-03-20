package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CategoryRequest(
        @NotNull(message = "Name shouldn't be null!")
        @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String name
) {
}
