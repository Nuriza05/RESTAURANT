package peaksoft.service.serviceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.StopListRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse save(StopListRequest request) {
        MenuItem menuItem = menuItemRepository.findById(request.menuItemId()).orElseThrow(() -> new NoSuchElementException("MenuItem is not"));
        Boolean exists = stopListRepository.existsByMenuItem(menuItem);
        if (exists && menuItem.getStopList().getDate().equals(request.date())) {
            throw new KeyAlreadyExistsException("This info already exist!");
        } else {
            StopList stopList = new StopList();
            stopList.setDate(request.date());
            stopList.setReason(request.reason());
            stopList.setMenuItem(menuItem);
            menuItem.setStopList(stopList);
            stopListRepository.save(stopList);
            return SimpleResponse.builder().status(HttpStatus.OK).message("StopList is saved!").build();
        }
    }

    @Override
    public List<StopListResponse> getAll() {
        return stopListRepository.getAllStops();
    }

    @Override
    public StopListResponse getById(Long id) {
        return stopListRepository.getStopById(id).orElseThrow(() -> new NoSuchElementException("StopList database de jok!"));
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest request) {
        System.out.println(request.menuItemId());
        System.out.println(id);
        StopList st = stopListRepository.findById(id).orElseThrow(() -> new NoSuchElementException("StopList database de jok!"));
        MenuItem menuItem = menuItemRepository.findById(request.menuItemId()).orElseThrow(() -> new NoSuchElementException("Myndai Menu jok"));
//        Boolean exists = stopListRepository.existsByMenuItem(menuItem);
//        if (exists && menuItem.getStopList().getDate().equals(request.date())) {
//            throw new KeyAlreadyExistsException("This info already exist!");
//        } else {
            st.setMenuItem(menuItem);
            st.setReason(request.reason());
            st.setDate(request.date());
            stopListRepository.save(st);
            return SimpleResponse.builder().status(HttpStatus.OK).message("It is successfully updated!").build();
        }

    @Override
    @Transactional
    public SimpleResponse deleteById(Long id) {
        StopList st = stopListRepository.findById(id).orElseThrow(() -> new NoSuchElementException("StopList jok"));
        st.getMenuItem().setInStock(true);
        stopListRepository.delete(id);
        return SimpleResponse.builder().status(HttpStatus.OK).message("It is deleted!").build();
    }
}
