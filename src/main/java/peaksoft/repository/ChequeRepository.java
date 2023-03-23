package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.entity.Cheque;

import java.util.List;
import java.util.Optional;
@Repository

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select avg(c.grandTotal) from Cheque c where c.user.restaurant.id=:restId")
    Double getAverageSum(Long restId);

}