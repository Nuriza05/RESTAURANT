package peaksoft.dto.responses;

import lombok.Data;
import peaksoft.entity.MenuItem;

import java.util.List;
@Data
public class PaginationResponse {
    private List<MenuItemResponse> menuItemList;
    private int currentPage;
    private int pageSize;

}
