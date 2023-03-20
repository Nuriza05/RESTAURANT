package peaksoft.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.service.RestaurantService;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApi {
    private final RestaurantService restaurantService;
    @Autowired
    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody RestaurantRequest request){
        return restaurantService.save(request);
    }

    @GetMapping
    public List<RestaurantResponse> getAll(){
        return restaurantService.getAll();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody RestaurantRequest request ){
        return restaurantService.update(id,request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return restaurantService.deleteById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id){
        return restaurantService.getById(id);
    }
}
