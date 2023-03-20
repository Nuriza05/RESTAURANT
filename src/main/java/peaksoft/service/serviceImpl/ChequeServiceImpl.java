package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @created : Lenovo Nuriza
 **/
@Service
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse save(ChequeRequest request) {
        double count = 0;
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new NoSuchElementException("Cheque is no exist!"));
        Cheque cheque = new Cheque();
        cheque.setUser(user);
        for (MenuItem menuItem : menuItemRepository.findAllById(request.menuItemsId())) {
            cheque.addMenuItem(menuItem);
            count += menuItem.getPrice();
        }
        cheque.setPriceAverage(count);
        cheque.setCreatedAt(LocalDate.now());
        double total = (count*cheque.getUser().getRestaurant().getService())/100;
        cheque.setGrandTotal(count+total);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Cheque is saved!").build();
    }

    @Override
    public List<ChequeResponse> getAll() {
        return chequeRepository.getAllChecks();
    }

    @Override
    public ChequeResponse getById(Long id) {
        return chequeRepository.getCHeckById(id).orElseThrow(() -> new NoSuchElementException("Check not found!"));
    }

    @Transactional
    @Override
    public SimpleResponse update(Long id, ChequeRequest request) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Check not found!"));
        List<MenuItem> allById = menuItemRepository.findAllById(request.menuItemsId());
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new NoSuchElementException("User not found!"));
        cheque.setMenuItems(allById);
        cheque.setUser(user);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully updated!").build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        chequeRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Successfully deleted!").build();
    }

    @Override
    public Double getAllChequesByUser(Long userId) {
        double count = 0;
        for (Cheque cheque : chequeRepository.findAll()) {
            if(cheque.getUser().getId().equals(userId) && cheque.getCreatedAt().equals(LocalDate.now())){
              count += cheque.getGrandTotal();
            }
        }
        return count;
    }

    @Override
    public Double getAverageSum(Long restId) {
        double count = 0;
        for (Cheque cheque : chequeRepository.findAll()) {
            if(cheque.getUser().getRestaurant().getId().equals(restId) && cheque.getCreatedAt().equals(LocalDate.now())){
                count += cheque.getGrandTotal();
            }
        }
        return count;
    }
}
