package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.SubcategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.SubcategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository sbrepo;
    private final CategoryRepository ctrepo;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository sbrepo, CategoryRepository ctrepo) {
        this.sbrepo = sbrepo;
        this.ctrepo = ctrepo;
    }


    @Override
    public SimpleResponse save(SubcategoryRequest request) {
        Subcategory sb = new Subcategory();
        Category category = ctrepo.findById(request.categoryId()).orElseThrow(() -> new NotFoundException("Subcateory with id: "+sb.getId()+" is no exist!"));
        sb.setName(request.name());
        sb.setCategory(category);
        category.addSubcategories(sb);
        sbrepo.save(sb);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Subcategory with id: "+sb.getId()+" is successfully saved!").build();
    }

    @Override
    public List<SubcategoryResponse> getByCategory(String word) {
        if (word == null) {
            return sbrepo.getAllSb();
        } else {
            return sbrepo.getByCategory(word);
        }
    }

    @Override
    public SubcategoryResponse getById(Long sbcId) {
        return sbrepo.getSbById(sbcId).orElseThrow(() -> new NotFoundException("SubCategory with id: "+sbcId+" is no exist!"));
    }

    @Override
    public SimpleResponse update(Long sbcId, SubcategoryRequest request) {
        Subcategory sb = sbrepo.findById(sbcId).orElseThrow(() -> new NotFoundException("SubCategory with id: "+sbcId+" is no exist!"));
        Category category = ctrepo.findById(request.categoryId()).orElseThrow(() -> new NotFoundException(("Category with id: "+request.categoryId()+" is no exist!")));
        sb.setCategory(category);
        sb.setName(request.name());
        sbrepo.save(sb);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Subcategory with id: "+sb.getId()+" is successfully saved!").build();
    }

    @Override
    public SimpleResponse deleteById(Long sbcId) {
        sbrepo.deleteById(sbcId);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Subcategory with id: "+sbcId+" is successfully saved!").build();
    }

    @Override
    public Map<String, List<SubcategoryResponse>> filterByCategory() {
        return sbrepo.getAllSb().stream().collect(Collectors.groupingBy(SubcategoryResponse::categoryName));
    }
}
