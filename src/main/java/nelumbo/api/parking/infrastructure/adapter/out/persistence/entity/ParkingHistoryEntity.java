package nelumbo.api.parking.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String plate;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "exit_date", nullable = false)
    private LocalDateTime exitDate;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private ParkingEntity parking;
}
