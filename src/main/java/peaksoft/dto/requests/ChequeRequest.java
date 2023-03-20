package peaksoft.dto.requests;

import lombok.Builder;
import peaksoft.entity.MenuItem;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record ChequeRequest(
        Long userId,
        List<Long> menuItemsId
) {

}
