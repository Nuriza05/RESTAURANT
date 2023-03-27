package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import peaksoft.entity.MenuItem;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record ChequeRequest(
        @NotNull(message = "User id shouldn't be null!")

        Long userId,
        @NotNull(message = "Menu items id shouldn't be null!")

        List<Long> menuItemsId
) {

}
