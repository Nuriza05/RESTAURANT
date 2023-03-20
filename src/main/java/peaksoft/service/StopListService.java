package peaksoft.service;

import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.StopListResponse;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface StopListService {
    SimpleResponse save(StopListRequest request);
    List<StopListResponse> getAll();
    StopListResponse getById(Long id);
    SimpleResponse update(Long id, StopListRequest request);
    SimpleResponse deleteById(Long id);

}
