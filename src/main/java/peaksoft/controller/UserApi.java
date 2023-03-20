package peaksoft.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.UserRequest;
import peaksoft.dto.requests.UserRequestFY;
import peaksoft.dto.requests.UserTokenRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.UserResponse;
import peaksoft.dto.responses.UserTokenResponse;
import peaksoft.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody UserTokenRequest request) {
        return userService.authenticate(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody UserRequest request) {
        return userService.saveUserByAdmin(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @PostMapping("/app")
    public SimpleResponse saveApp(@RequestBody @Valid UserRequestFY requestFY){
        return userService.userApp(requestFY);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public List<UserResponse> jobApplication(@RequestParam(required = false) Long id, @RequestParam String word) {
        return userService.jobApplication(id, word);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll/{restId}")
    public List<UserResponse> getAll(@PathVariable Long restId) {
        return userService.getAll(restId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/get/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody UserRequest request) {
        return userService.update(userId, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }

}
