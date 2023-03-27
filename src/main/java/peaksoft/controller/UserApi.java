package peaksoft.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.UserRequest;
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
    public SimpleResponse save(@RequestBody @Valid UserRequest request) {
        return userService.saveUserByAdmin(request);
    }

    @PermitAll
    @PostMapping("/app")
    public SimpleResponse saveApp(@RequestBody @Valid UserRequest request){
        return userService.userApp(request);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public List<UserResponse> jobApplication(@RequestParam(required = false) Long id, @RequestParam String word) {
        return userService.jobApplication(id, word);
    }

    @PermitAll
    @GetMapping("/getAll/{restId}")
    public List<UserResponse> getAll(@PathVariable Long restId) {
        return userService.getAll(restId);
    }

    @PermitAll
    @GetMapping("/get/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody @Valid UserRequest request) {
        return userService.update(userId, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }

}
