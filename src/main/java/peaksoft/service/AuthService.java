package peaksoft.service;

import peaksoft.dto.requests.AuthRequest;
import peaksoft.dto.responses.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);

}
