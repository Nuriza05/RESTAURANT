package peaksoft.service;

import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.dto.responses.SimpleResponse;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface ChequeService {
    SimpleResponse save(ChequeRequest request);
    List<ChequeResponse> getAll();
    ChequeResponse getById(Long id);
    SimpleResponse update(Long id, ChequeRequest request);
    SimpleResponse deleteById(Long id);
    Double getAllChequesByUser(Long userId);
    Double getAverageSum(Long restId);

}
