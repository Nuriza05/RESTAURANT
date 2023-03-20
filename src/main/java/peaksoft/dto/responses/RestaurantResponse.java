package peaksoft.dto.responses;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @created : Lenovo Nuriza
 **/
@Builder

public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees
) {
}
