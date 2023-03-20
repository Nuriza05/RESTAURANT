package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.SubcategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.dto.responses.UserResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.service.SubcategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryApi {
    private final SubcategoryService subcategoryService;

    @Autowired
    public SubcategoryApi(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody SubcategoryRequest request){
       return subcategoryService.save(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public SubcategoryResponse getById(@PathVariable Long id){
        return subcategoryService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @GetMapping("/get")
    public List<SubcategoryResponse> get(@RequestParam(required = false) String word){
        return subcategoryService.getByCategory(word);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody SubcategoryRequest request){
        return subcategoryService.update(id,request);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return subcategoryService.deleteById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/grouping")
    public Map<String,List<SubcategoryResponse>> filter(){
        return subcategoryService.filterByCategory();
    }
}
