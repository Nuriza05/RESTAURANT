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
        @Positive(message = "user id should be positive number!")
        Long userId,
        @NotNull(message = "Menu items id shouldn't be null!")
        @Positive(message = "menu items id should be positive number!")
        List<Long> menuItemsId
) {

}
