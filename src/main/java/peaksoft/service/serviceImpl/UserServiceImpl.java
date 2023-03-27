package peaksoft.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.requests.UserRequest;
import peaksoft.dto.requests.UserTokenRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.UserResponse;
import peaksoft.dto.responses.UserTokenResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.util.List;


/**
 * @created : Lenovo Nuriza
 **/
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository repository, RestaurantRepository restaurantRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserTokenResponse authenticate(UserTokenRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException(String.format
                        ("User with email: %s doesn't exists", request.email())));
        String token = jwtUtil.generateToken(user);

        return UserTokenResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }

    @PostConstruct
    @Override
    public void init() {
        User user = new User();
        user.setRole(Role.ADMIN);
        user.setDateOfBirth(LocalDate.of(2005, 4, 5));
        user.setFirstName("Admin");
        user.setLastName("Adminova");
        user.setPhoneNumber("+99655487654");
        user.setEmail("admin@gmail.com");
        user.setPassword(encoder.encode("admin123"));
        user.setExperience(3);
        if (!repository.existsByEmail("admin@gmail.com")) {
            repository.save(user);
        }
    }


    @Override
    public SimpleResponse saveUserByAdmin(UserRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.restaurantId()).orElseThrow(() -> new NotFoundException("Restaurant with id: " + request.restaurantId() + " is no exist!"));
        Boolean exists = repository.existsByEmail(request.email());
        List<UserResponse> users = repository.getAllUsers(restaurant.getId());
        check(request);
        if (!exists) {
            if (users.size() <= 15) {
                User user = new User();
                user.setFirstName(request.firstName());
                user.setLastName(request.lastName());
                user.setDateOfBirth(request.dateOfBirth());
                user.setEmail(request.email());
                user.setPassword(encoder.encode(request.password()));
                user.setPhoneNumber(request.phoneNumber());
                user.setExperience(request.experience());
                user.setRole(request.role());
                user.setRestaurant(restaurant);
                repository.save(user);
                return SimpleResponse.builder().status(HttpStatus.OK).message("User with id: " + user.getId() + " is saved").build();
            } else {
                return SimpleResponse.builder().status(HttpStatus.BAD_REQUEST).message("No vacancy").build();
            }
        } else {
            return SimpleResponse.builder().status(HttpStatus.CONFLICT).message("Already exist email").build();
        }
    }

    @Override
    public SimpleResponse userApp(UserRequest request) {
        Boolean exists = repository.existsByEmail(request.email());
        check(request);
        if (!exists) {
            User user = new User();
            user.setFirstName(request.firstName());
            user.setLastName(request.lastName());
            user.setDateOfBirth(request.dateOfBirth());
            user.setEmail(request.email());
            user.setPassword(encoder.encode(request.password()));
            user.setPhoneNumber(request.phoneNumber());
            user.setExperience(request.experience());
            user.setRole(request.role());
            repository.save(user);
        } else {
            return SimpleResponse.builder().status(HttpStatus.CONFLICT).message("Email already exist!").build();
        }
        return null;
    }


    @Override
    public List<UserResponse> jobApplication(Long id, String word) {
        Restaurant restaurant = restaurantRepository.findById(restaurantRepository.findAll().get(0).getId()).orElseThrow(() -> new NotFoundException("Restaurant with no exist"));
        if (word.equalsIgnoreCase("Vacancy")) {
            return repository.getAllApp();
        } else if (word.equalsIgnoreCase("accept")) {
            List<UserResponse> users = repository.getAllUsers(restaurant.getId());
            if (users.size() <= 15) {
                assignUserToRest(id,restaurantRepository.findAll().get(0).getId() );
            } else
                throw new BadCredentialException("No vacancy");
        } else if (word.equalsIgnoreCase("cancel")) {
            deleteById(id);
        } else {
            throw new BadCredentialException("User id or keyWord not matched!");
        }
        return null;
    }

    @Override
    public SimpleResponse assignUserToRest(Long userId, Long restId) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist!"));
        Restaurant rest = restaurantRepository.findById(restId).orElseThrow(() -> new NotFoundException("Restaurant with id:" + restId + "is no exist"));
        user.setRestaurant(rest);
        rest.addUser(user);
        repository.save(user);
        restaurantRepository.save(rest);
        return SimpleResponse.builder().status(HttpStatus.OK).message("User with id:" + user.getId() + " is successfully assigned!").build();
    }

    @Override
    public UserResponse getById(Long userId) {
        return repository.getUserById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist!"));
    }

    @Override
    public List<UserResponse> getAll(Long restId) {
        restaurantRepository.findById(restId).orElseThrow(()->new NotFoundException("Restaurant with id: "+restId+" is no exist!"));
        return repository.getAllUsers(restId);
    }

    @Transactional
    @Override
    public SimpleResponse update(Long userId, UserRequest request) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist!"));
        List<User> all = repository.findAll();
        all.remove(user);
        check(request);
        for (User user1 : all) {
            if (!user1.getEmail().equals(request.email())) {
                user.setFirstName(request.firstName());
                user.setLastName(request.lastName());
                user.setDateOfBirth(request.dateOfBirth());
                user.setEmail(request.email());
                user.setPassword(request.password());
                user.setPhoneNumber(request.phoneNumber());
                user.setExperience(request.experience());
                repository.save(user);
                return SimpleResponse.builder().status(HttpStatus.OK).message("User with id: " + userId + " is updated!").build();
            } else {
                return SimpleResponse.builder().status(HttpStatus.FORBIDDEN).message("Email is already exists!").build();
            }
        }
        return null;
    }

    @Override
    public SimpleResponse deleteById(Long userId) {
        repository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist"));
        repository.deleteById(userId);
        return SimpleResponse.builder().status(HttpStatus.OK).message("User with id: " + userId + " is deleted!").build();
    }

    private void check(UserRequest request) {
        Boolean existsPh = repository.existsByPhoneNumber(request.phoneNumber());
        if (existsPh) {
            throw new AlreadyExistException("User with phone number: " + request.phoneNumber() + " is already exist!");
        }
        if (!request.phoneNumber().startsWith("+996")) {
            throw new BadRequestException("Phone number should starts with +996");
        }
        int year = LocalDate.now().minusYears(request.dateOfBirth().getYear()).getYear();
        if (request.role().equals(Role.CHEF)) {
            if (year <= 25 || year >= 45 && request.experience() <= 2) {
                throw new BadRequestException("Chef's years old should be between 25-45 and experience>=2");
            }
        } else if (request.role().equals(Role.WAITER)) {
            if (year <= 18 || year >= 30 && request.experience() <= 1) {
                throw new BadRequestException("Waiter's years old should be between 18-30 and experience>=1");
            }
        }
    }
}
