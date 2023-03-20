package peaksoft.service;

import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface RestaurantService {
    SimpleResponse save(RestaurantRequest request);
    List<RestaurantResponse> getAll();
    SimpleResponse update(Long resId, RestaurantRequest request);
    SimpleResponse deleteById(Long resId);
    RestaurantResponse getById(Long resId);
}
