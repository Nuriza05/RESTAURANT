package peaksoft.dto.responses;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record ChequeResponse(
        Long id,
        String fullName,
        List<MenuItem> items,
        double averagePrice,
        double service,
        double grandTotal
) {
}
