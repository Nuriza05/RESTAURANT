package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.entity.Subcategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    @Query("select new peaksoft.dto.responses.SubcategoryResponse(s.category.name,s.id,s.name) from Subcategory s")
    List<SubcategoryResponse> getAllSb();

    @Query("select new peaksoft.dto.responses.SubcategoryResponse(s.category.name,s.id,s.name) from Subcategory s where s.id=:id")
    Optional<SubcategoryResponse> getSbById(Long id);
    @Query("select new peaksoft.dto.responses.SubcategoryResponse(s.category.name,s.id,s.name) from Subcategory s where s.category.name ilike %:word% order by s.name")
    List<SubcategoryResponse> getByCategory(String word);
    Boolean existsByName(String name);
}