package peaksoft.dto.responses;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.time.LocalDate;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record StopListResponse(
        String menuItemName,
        Long id,
        String reason,
        LocalDate date
) {
}
