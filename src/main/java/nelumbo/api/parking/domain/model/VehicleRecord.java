package nelumbo.api.parking.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String plate;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;
}
