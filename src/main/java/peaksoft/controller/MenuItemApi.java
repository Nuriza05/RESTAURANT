package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menuItems")
public class MenuItemApi {
    private final MenuItemService menuItemService;
    @Autowired
    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid MenuItemRequest request){
        return menuItemService.save(request);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public MenuItemResponse getById(@PathVariable Long id){
        return menuItemService.getById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid MenuItemRequest request){
        return menuItemService.update(id,request);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return menuItemService.deleteById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping(("/search"))
    public List<MenuItemResponse> globalSearch(@RequestParam(required = false) @Valid String word){
        return menuItemService.globalSearch(word);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/sort")
    public List<MenuItemResponse> sortByPrice(@RequestParam String word){
        return menuItemService.sortByPrice(word);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/grouping")
    public Map<Boolean, List<MenuItemResponse>> filter(){
        return menuItemService.filterByVegetarian();
    }



}
