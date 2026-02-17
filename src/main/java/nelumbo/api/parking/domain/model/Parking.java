package nelumbo.api.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parking {
    private Long id;
    private String name;
    private Integer capacity;
    private Double costPerHour;
    private User socio;
    private LocalDateTime deletedAt;
}
