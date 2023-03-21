package peaksoft.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;
import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * @created : Lenovo Nuriza
 **/
@Builder
public record
UserRequest(
        @NotNull(message = "restaurant id shouldn't be null!")
        @Positive(message = "restaurant id should be positive number!")
        Long restaurantId,
        @NotNull(message = "firstName shouldn't ne null!")
        @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String firstName,
        @NotNull(message = "lastName shouldn't be null")
        @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
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
        @Positive(message = "experience should be positive number!")
        int experience,
        @NotNull(message = "experience shouldn't be null!")
        Role role

) {
}
