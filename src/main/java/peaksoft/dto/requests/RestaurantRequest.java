package peaksoft.dto.requests;

import lombok.Builder;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record RestaurantRequest(
        String name,
        String location,
        String restType,
        double service
) {
}
