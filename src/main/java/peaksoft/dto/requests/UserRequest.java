package peaksoft.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;
import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record UserRequest(
        @NotNull(message = "restaurant id shouldn't be null!")
        Long restaurantId,
        @NotNull(message = "firstName shouldn't ne null!")
        String firstName,
        @NotNull(message = "lastName shouldn't be null")
        String lastName,
        @NotNull(message = "Date of birth shouldn't be null")
        LocalDate dateOfBirth,
        @NotNull(message = "Email shouldn't be null")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "password shouldn't be null")
        @Length(min = 4, max = 15, message = "password size should be between 4 and 15")
        String password,
        @NotNull(message = "phone number shouldn't be null!")
        @Length(min = 13, max = 13, message = "phone number length should be 13")
        String phoneNumber,
        @NotNull(message = "experience shouldn't be null!")
        int experience,
        @NotNull(message = "experience shouldn't be null!")
        Role role

) {
}
