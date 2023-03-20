package peaksoft.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.MenuItemRequest;
import peaksoft.dto.responses.MenuItemResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.Subcategory;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, SubcategoryRepository subcategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public SimpleResponse save(MenuItemRequest request) {
        if(request.price()>=1) {
            Restaurant restaurant = restaurantRepository.findById(request.restaurantId()).orElseThrow(() -> new NoSuchElementException("Restaurant with id: " + request.restaurantId() + " is no exist!"));
            Subcategory subcategory = subcategoryRepository.findById(request.subcategoryId()).orElseThrow(() -> new NoSuchElementException("Subcategory with id: " + request.subcategoryId() + " is no exist!"));
            MenuItem menuItem = new MenuItem();
            menuItem.setRestaurant(restaurant);
            menuItem.setSubcategory(subcategory);
            menuItem.setName(request.name());
            menuItem.setImage(request.image());
            menuItem.setPrice(request.price());
            menuItem.setDescription(request.description());
            menuItem.setVegetarian(request.isVegetarian());
            menuItem.setInStock(true);
            menuItemRepository.save(menuItem);
            return SimpleResponse.builder().status(HttpStatus.OK).message("MenuItem with id: " + menuItem.getId() + " is saved!").build();
        }else {
            return SimpleResponse.builder().status(HttpStatus.OK).message("Price shouldn't be negative number!").build();
        }
    }

    @Override
    public List<MenuItemResponse> getAll() {
        return menuItemRepository.getAllMenus();
    }


    @Override
    public MenuItemResponse getById(Long menuId) {
        return menuItemRepository.getMenuById(menuId).orElseThrow(()->new NoSuchElementException("Menu with id: "+menuId+" is no exist!"));

    }

    @Override
    public SimpleResponse update(Long menuId, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(()->new NoSuchElementException("Menu with id: "+menuId+" is no exist!"));
        menuItem.setName(request.name());
        menuItem.setImage(request.image());
        menuItem.setPrice(request.price());
        menuItem.setDescription(request.description());
        menuItem.setVegetarian(request.isVegetarian());
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Menu with id: "+menuId+" is updated!").build();
    }

    @Override
    public SimpleResponse deleteById(Long menuId) {
        menuItemRepository.deleteById(menuId);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Menu with id: "+menuId+" is deleted!").build();
    }

    @Override
    public List<MenuItemResponse> globalSearch(String word) {
        if(word==null){
            return menuItemRepository.getAllMenus();
        }else {
            return menuItemRepository.globalSearch(word);
        }
    }

    @Override
    public List<MenuItemResponse> sortByPrice(String word) {
        if(word.equalsIgnoreCase("Asc")){
           return menuItemRepository.sortByPriceAsc();
        }else if(word.equalsIgnoreCase("Desc")) {
            return menuItemRepository.sortByPriceDesc();
        }else {
            return menuItemRepository.getAllMenus();
        }
    }

    @Override
    public Map<Boolean, List<MenuItemResponse>> filterByVegetarian() {
      return menuItemRepository.getAllMenus().stream().collect(Collectors.groupingBy(MenuItemResponse::IsVegetarian));
    }
}

