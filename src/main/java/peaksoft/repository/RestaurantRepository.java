package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.RestaurantResponse;
import peaksoft.entity.Restaurant;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new peaksoft.dto.responses.RestaurantResponse(l.id,l.name,l.location,l.restType,l.numberOfEmployees) from Restaurant l")
    List<RestaurantResponse> getAllRest();

    @Query("select new peaksoft.dto.responses.RestaurantResponse(l.id,l.name,l.location,l.restType,l.numberOfEmployees) from Restaurant l where l.id=:id")
      RestaurantResponse getResById(Long id);
}