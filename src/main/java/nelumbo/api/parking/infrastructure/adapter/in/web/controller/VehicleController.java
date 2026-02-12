package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.port.in.VehicleQueryService;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ActiveVehicleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleQueryService vehicleQueryService;

    @GetMapping("/active/{parkingId}")
    @PreAuthorize("hasAuthority('VIEW_VEHICLES')")
    public ResponseEntity<List<ActiveVehicleResponse>> getActiveVehicles(@PathVariable Long parkingId) {
        return ResponseEntity.ok(vehicleQueryService.findActiveVehiclesByParking(parkingId).stream()
                .map(record -> new ActiveVehicleResponse(
                        record.getId(),
                        record.getPlate(),
                        record.getEntryDate(),
                        record.getParking().getName()))
                .collect(Collectors.toList()));
    }
}
