package peaksoft.service;

import peaksoft.dto.requests.SubcategoryRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;

import java.util.List;
import java.util.Map;

/**
 * @created : Lenovo Nuriza
 **/

public interface SubcategoryService {
    SimpleResponse save(SubcategoryRequest request);
    List<SubcategoryResponse> getByCategory(String word);
    SubcategoryResponse getById(Long sbcId);
    SimpleResponse update(Long sbcId, SubcategoryRequest request);
    SimpleResponse deleteById(Long sbcId);
    Map<String,List<SubcategoryResponse>> filterByCategory();



}
