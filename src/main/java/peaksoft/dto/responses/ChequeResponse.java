package peaksoft.dto.responses;

import lombok.Builder;
import lombok.Data;
import peaksoft.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @created : Lenovo Nuriza
 **/
@Data
public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItem> items;
    private double averagePrice;
    private double service;
    private double grandTotal;

}
