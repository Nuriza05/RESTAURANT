package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Subcategory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new peaksoft.dto.responses.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.InStock=true")
    List<MenuItemResponse> getAllMenus();
    @Query(value = "select l from MenuItem l where  l.InStock=true ")
    List<MenuItem> findAll();


    @Query("select new peaksoft.dto.responses.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id=:id and m.InStock=true ")
    Optional<MenuItemResponse> getMenuById(Long id);

    @Query("select new peaksoft.dto.responses.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where  m.InStock=true order by m.price ")
    List<MenuItemResponse> sortByPriceAsc();

    @Query("select new peaksoft.dto.responses.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where  m.InStock=true order by m.price desc ")
    List<MenuItemResponse> sortByPriceDesc();
    @Query("select new peaksoft.dto.responses.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where  m.InStock= true and lower(m.name) like lower(concat('%',:word,'%'))or " +
            "lower(m.subcategory.name) like lower(concat('%', :word, '%')) or " +
            "lower(m.subcategory.category.name) like lower(concat('%', :word, '%')) ")
    List<MenuItemResponse> globalSearch(String word);

    Boolean existsByName(String name);

    Page<MenuItemResponse> findAllBy(Pageable pageable);
}