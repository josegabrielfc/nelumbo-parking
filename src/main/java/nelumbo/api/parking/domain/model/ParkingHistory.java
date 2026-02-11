package nelumbo.api.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingHistory {
    private Long id;
    private String plate;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private Double totalCost;
    private Parking parking;
}
