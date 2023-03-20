package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopList")
public class StopListApi {
    private final StopListService stopListService;

    @Autowired
    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody StopListRequest request) {
        return stopListService.save(request);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping
    public List<StopListResponse> getAll(){
        return stopListService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public StopListResponse getById(@PathVariable Long id){
        return stopListService.getById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, StopListRequest request){
        return stopListService.update(id,request);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return stopListService.deleteById(id);
    }
}
