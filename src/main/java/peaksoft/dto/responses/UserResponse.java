package peaksoft.dto.responses;

import lombok.Builder;

import java.time.LocalDate;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record UserResponse(
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        int experience
) {
}
