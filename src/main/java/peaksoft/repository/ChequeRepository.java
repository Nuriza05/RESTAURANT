package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.entity.Cheque;

import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new peaksoft.dto.responses.ChequeResponse(l.id,concat(l.user.firstName,l.user.lastName),l.menuItems,l.priceAverage,l.user.restaurant.service,l.grandTotal) from Cheque l")
    List<ChequeResponse> getAllChecks();

    @Query("select new peaksoft.dto.responses.ChequeResponse(l.id,concat(l.user.firstName,l.user.lastName),l.menuItems,l.priceAverage,l.user.restaurant.service,l.grandTotal) from Cheque l where l.id=:id")
    Optional<ChequeResponse> getCHeckById(Long id);
//    @Query("select c from Cheque c where c.user.id=:userId and c.createdAt=:now")
//    List<Check> getCheckOfUser(Long userId, LocalDate now);
}