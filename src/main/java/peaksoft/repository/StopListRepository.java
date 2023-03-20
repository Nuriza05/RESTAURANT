package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;

import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.responses.StopListResponse(s.menuItem.name,s.id,s.reason,s.date) from StopList s")
     List<StopListResponse> getAllStops();

    @Query("select new peaksoft.dto.responses.StopListResponse(s.menuItem.name,s.id,s.reason,s.date) from StopList s where s.id=:id")
    Optional<StopListResponse> getStopById(Long id);

    Boolean existsByMenuItem(MenuItem menuItem);

    @Modifying
    @Query("delete from StopList t where t.id = ?1")
    void delete(Long entityId);
}