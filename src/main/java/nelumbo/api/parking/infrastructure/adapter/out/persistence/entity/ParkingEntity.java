package nelumbo.api.parking.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "cost_per_hour", nullable = false)
    private Double costPerHour;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity socio;
}
