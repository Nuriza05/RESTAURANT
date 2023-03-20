package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new peaksoft.dto.responses.CategoryResponse(c.id,c.name) from Category c")
    List<CategoryResponse> getAllCat();

    @Query("select new peaksoft.dto.responses.CategoryResponse(c.id,c.name) from Category c where c.id=:id")
    Optional<CategoryResponse> getCatById(Long id);
}