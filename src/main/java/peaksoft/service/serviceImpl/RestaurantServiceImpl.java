package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepo;
    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public SimpleResponse save(RestaurantRequest request) {
        List<Restaurant> all = restaurantRepo.findAll();
        if(all.isEmpty()) {
            Restaurant rest = new Restaurant();
            rest.setName(request.name());
            rest.setRestType(request.restType());
            rest.setLocation(request.location());
            rest.setService(request.service());
            restaurantRepo.save(rest);
            return SimpleResponse.builder().status(HttpStatus.OK).message("Restaurant with name: " + rest.getName() + " is saved!").build();
        }
        else {
            throw new BadRequestException("Restaurant is already exist!");
        }
    }

    @Transactional
    @Override
    public SimpleResponse update(Long resId, RestaurantRequest request) {
        Restaurant rest = restaurantRepo.findById(resId).orElseThrow(() -> new NotFoundException("Restaurant with id: " + resId + " is no exist!"));
        rest.setName(request.name());
        rest.setLocation(request.location());
        rest.setRestType(request.restType());
        rest.setService(request.service());
        restaurantRepo.save(rest);
        return SimpleResponse.builder().status(HttpStatus.OK).message( "Restaurant with name: " + rest.getName() + " is updated!!").build();
    }
    @Override
    public SimpleResponse deleteById(Long resId) {
        restaurantRepo.findById(resId).orElseThrow(()->new NotFoundException("Restaurant with id: "+resId+" is no exist"));
        restaurantRepo.deleteById(resId);
        return SimpleResponse.builder().status(HttpStatus.OK).message( "Restaurant with name: " + resId+ " is deleted!").build();
    }

    @Override
    public RestaurantResponse getById(Long resId) {

        Restaurant restaurant = restaurantRepo.findById(resId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + resId + " not found!"));
        restaurant.setNumberOfEmployees(restaurant.getUsers().size());

        restaurantRepo.save(restaurant);

        return restaurantRepo.getResById(resId);


    }
}
