package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SimpleResponse save(CategoryRequest category) {
        Category category1 = new Category();
        category1.setName(category.name());
        categoryRepository.save(category1);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Category with id: "+category1.getId()+" is saved!").build();
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAllCat();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.getCatById(id).orElseThrow(()->new NotFoundException("Category with id: "+id+" is no exist!"));
    }

    @Override
    public SimpleResponse deleteById(Long id) {
         categoryRepository.deleteById(id);
         return SimpleResponse.builder().status(HttpStatus.OK).message("Category with id: "+id+" is deleted!").build();
    }

    @Override
    public SimpleResponse update(Long id, Category category) {
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " is no exist!"));
        category1.setName(category.getName());
        categoryRepository.save(category1);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Category with id: "+id+" is updated!").build();
    }
}
