package peaksoft.service;

import peaksoft.dto.requests.UserRequest;
import peaksoft.dto.requests.UserRequestFY;
import peaksoft.dto.requests.UserTokenRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.UserResponse;
import peaksoft.dto.responses.UserTokenResponse;

import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
public interface UserService {
    void init();
    SimpleResponse userApp(UserRequestFY requestFY);
    SimpleResponse saveUserByAdmin(UserRequest request);
    List<UserResponse> jobApplication(Long id, String word);
    SimpleResponse assignUserToRest(Long userId, Long restId);
    UserResponse getById(Long userId);
    List<UserResponse> getAll(Long restId);
    SimpleResponse update(Long userId, UserRequest request);
    SimpleResponse deleteById(Long userId);
    UserTokenResponse authenticate(UserTokenRequest request);
}
