package nelumbo.api.parking.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResponse {
    private Long id;
    private String name;
    private Integer capacity;
    private Double costPerHour;
    private Long socioId;
    private String socioName;
}
