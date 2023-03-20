package peaksoft.service;

import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Map;

/**
 * @created : Lenovo Nuriza
 **/
public interface MenuItemService {
    SimpleResponse save(MenuItemRequest request);
    List<MenuItemResponse> getAll();
    MenuItemResponse getById(Long menuId);
    SimpleResponse update(Long menuId, MenuItemRequest request);
    SimpleResponse deleteById(Long menuId);
    List<MenuItemResponse> globalSearch(String word);
    List<MenuItemResponse> sortByPrice(String word);
    Map<Boolean, List<MenuItemResponse>> filterByVegetarian();


}
