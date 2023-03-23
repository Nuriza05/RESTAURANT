package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.responses.UserResponse;
import peaksoft.entity.User;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    @Query("select new peaksoft.dto.responses.UserResponse(l.id,concat(l.firstName,l.lastName),l.dateOfBirth,l.email,l.phoneNumber,l.experience) from User l where l.restaurant.id=:resId")
    List<UserResponse> getAllUsers(Long resId);
    @Query("select new peaksoft.dto.responses.UserResponse(l.id,concat(l.firstName,l.lastName),l.dateOfBirth,l.email,l.phoneNumber,l.experience) from User l where l.id=:id")
    Optional<UserResponse> getUserById(Long id);
    @Query("select new peaksoft.dto.responses.UserResponse(l.id,concat(l.firstName,l.lastName),l.dateOfBirth,l.email,l.phoneNumber,l.experience) from User l where l.restaurant.id=null")
    List<UserResponse> getAllApp();

    Optional<User> findByEmail(String email);
}