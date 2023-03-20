package peaksoft.dto.requests;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListRequest(
        Long menuItemId,
        String reason,
        LocalDate date

){

}
