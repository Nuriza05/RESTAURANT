package peaksoft.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.service.CategoryService;
import java.util.List;


/**
 * @created : Lenovo Nuriza
 **/
@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    private final CategoryService categoryService;
    @Autowired
    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid CategoryRequest category){
        return categoryService.save(category);
    }
    @PermitAll
    @GetMapping
    public List<CategoryResponse> getAll(){
        return categoryService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public CategoryResponse
    getById(@PathVariable Long id){
        return categoryService.getById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid Category category){
        return categoryService.update(id,category);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return categoryService.deleteById(id);
    }

}
