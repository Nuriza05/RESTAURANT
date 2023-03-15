package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.AuthInfo;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    Optional<AuthInfo> findByEmail(String email);

    boolean existsByEmail(String email);

//    @Query("select case when count(u) > 0 then true else false end from AuthInfo u where u.role = 'ADMIN'")
//    Boolean existByAdmin();


}