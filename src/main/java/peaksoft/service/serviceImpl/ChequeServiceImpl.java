package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.ChequeResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @created : Lenovo Nuriza
 **/
@Service
@Slf4j
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
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new NotFoundException("User with id: " + request.userId() + " is no exist!"));
        Cheque cheque = new Cheque();
        cheque.setUser(user);
        for (MenuItem menuItem : menuItemRepository.findAllById(request.menuItemsId())) {
            cheque.addMenuItem(menuItem);
            count += menuItem.getPrice();
        }
        cheque.setPriceAverage(count);
        cheque.setCreatedAt(LocalDate.now());
        double total = (count * cheque.getUser().getRestaurant().getService()) / 100;
        cheque.setGrandTotal(count + total);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Cheque with id: " + cheque.getId() + " is saved!").build();
    }

    @Override
    public List<ChequeResponse> getAll() {
        List<Cheque> cheques = chequeRepository.findAll();
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        ChequeResponse chequeResponse = new ChequeResponse();
        for (Cheque che : cheques) {
            chequeResponse.setId(che.getId());
            chequeResponse.setFullName(che.getUser().getFirstName() + che.getUser().getLastName());
            chequeResponse.setItems(che.getMenuItems());
            chequeResponse.setAveragePrice(che.getPriceAverage());
            chequeResponse.setService(che.getUser().getRestaurant().getService());
            chequeResponse.setGrandTotal(che.getGrandTotal());
            chequeResponses.add(chequeResponse);
        }
        return chequeResponses;
    }

    @Override
    public ChequeResponse getById(Long id) {
        Cheque che = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cheque with id: " + id + " is no exist!"));
        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setId(che.getId());
        chequeResponse.setFullName(che.getUser().getFirstName() + che.getUser().getLastName());
        chequeResponse.setItems(che.getMenuItems());
        chequeResponse.setAveragePrice(che.getPriceAverage());
        chequeResponse.setService(che.getUser().getRestaurant().getService());
        chequeResponse.setGrandTotal(che.getGrandTotal());
        return chequeResponse;
    }

    @Transactional
    @Override
    public SimpleResponse update(Long id, ChequeRequest request) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Check with id: " + id + " not found!"));
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found!"));
        List<MenuItem> allById = menuItemRepository.findAllById(request.menuItemsId());
        cheque.setMenuItems(allById);
        cheque.setUser(user);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Cheque with id: " + cheque.getId() + " is successfully updated!").build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cheque with id: " + id + " is no exist"));
        chequeRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK).message("Cheque with id: " + id + " is successfully deleted!!").build();
    }

    @Override
    public Double getAllChequesByUser(Long userId) {
        double count = 0;
        for (Cheque cheque : chequeRepository.findAll()) {
            if (cheque.getUser().getId().equals(userId) && cheque.getCreatedAt().equals(LocalDate.now())) {
                count += cheque.getGrandTotal();
            }
        }
        return count;
    }

    @Override
    public Double getAverageSum(Long restId) {
        return chequeRepository.getAverageSum(restId);
    }
}
