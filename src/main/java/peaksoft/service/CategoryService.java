package peaksoft.service;

import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Category;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface CategoryService {
    SimpleResponse save(CategoryRequest category);
    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    SimpleResponse deleteById(Long id);
    SimpleResponse update(Long id, Category category);
}
