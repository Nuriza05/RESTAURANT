package peaksoft.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/api/cheques")
public class ChequeApi {
    private final ChequeService chequeService;

    @Autowired
    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping
    public SimpleResponse save(@RequestBody

                                   ChequeRequest request) {
        return chequeService.save(request);
    }

    @PermitAll
    @GetMapping
    public List<ChequeResponse> getAll() {
        return chequeService.getAll();
    }

    @PermitAll
    @GetMapping("/{id}")
    public ChequeResponse getById(@PathVariable Long id) {
        return chequeService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid ChequeRequest request) {
        return chequeService.update(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return chequeService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/getByUser/{id}")
    public Double getByUser(@PathVariable Long id) {
        return chequeService.getAllChequesByUser(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getByRest/{id}")
    public Double getByRest(@PathVariable Long id) {
        return chequeService.getAverageSum(id);
    }
}
