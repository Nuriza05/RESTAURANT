package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.CategoryRequest;
import peaksoft.dto.responses.CategoryResponse;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.entity.MenuItem;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.service.CategoryService;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, MenuItemRepository menuItemRepository) {
        this.categoryRepository = categoryRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse save(CategoryRequest category) {
        Category category1 = new Category();
        if (!categoryRepository.existsByName(category.name())) {
            category1.setName(category.name());
            categoryRepository.save(category1);
            return SimpleResponse.builder().status(HttpStatus.OK).message("Category with id: " + category1.getId() + " is saved!").build();
        } else {
            throw new AlreadyExistException("Category with name: " + category.name() + " is already exist!");
        }
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAllCat();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.getCatById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " is no exist!"));
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " is no exist!"));
        List<MenuItem> menus = menuItemRepository.getByCategoryId(id);
        for (MenuItem menu : menus) {
            menu.setSubcategory(null);
            menuItemRepository.deleteById(menu.getId());
        }
        categoryRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Category with id: " + id + " is deleted!").build();
    }

    @Override
    public SimpleResponse update(Long id, Category category) {
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category with id: " + id + " is no exist!"));
        List<Category> all = categoryRepository.findAll();
        all.remove(category1);
        for (Category category2 : all) {
            if (category2.getName().equals(category1.getName())) {
                throw new AlreadyExistException("Category with name: " + category.getName() + " is already exist!");
            } else {
                category1.setName(category.getName());
                categoryRepository.save(category1);
                return SimpleResponse.builder().status(HttpStatus.OK).message("Category with id: " + id + " is updated!").build();
            }
        }
        return null;
    }
}
