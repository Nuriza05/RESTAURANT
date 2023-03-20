package peaksoft.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;

@Builder
public record StopListRequest(
        @Positive(message = "menu item id should be positive number!")
        @NotNull(message = "menu item id shouldn't be null!")
        Long menuItemId,
        @NotNull(message = "reason shouldn't be null!")
        String reason,
        @NotNull(message = "date shouldn't be null!")
        LocalDate date

){

}
