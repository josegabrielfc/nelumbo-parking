package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.indicator.*;
import nelumbo.api.parking.domain.port.in.IndicatorUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ActiveVehicleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/indicators")
@RequiredArgsConstructor
public class IndicatorController {

    private final IndicatorUseCase indicatorUseCase;

    @GetMapping("/admin/top-vehicles")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<List<VehicleFrequency>> getTopVehicles() {
        return ResponseEntity.ok(indicatorUseCase.getTop10FrequentVehicles());
    }

    @GetMapping("/admin/top-socios")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<List<SocioEarnings>> getTopSocios() {
        return ResponseEntity.ok(indicatorUseCase.getTop3SociosByEarnings());
    }

    @GetMapping("/admin/top-parkings")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<List<ParkingFrequency>> getTopParkings() {
        return ResponseEntity.ok(indicatorUseCase.getTop10FrequentParkings());
    }

    @GetMapping("/admin/weekly-top-socios")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<List<SocioEntries>> getWeeklyTopSocios() {
        return ResponseEntity.ok(indicatorUseCase.getTop3SociosByWeeklyEntries());
    }

    @GetMapping("/admin/weekly-top-parkings")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<List<ParkingEarnings>> getWeeklyTopParkings() {
        return ResponseEntity.ok(indicatorUseCase.getTop3ParkingsByWeeklyEarnings());
    }

    @GetMapping("/socio/first-timers/{parkingId}")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<List<ActiveVehicleResponse>> getFirstTimeVehicles(@PathVariable Long parkingId) {
        return ResponseEntity.ok(indicatorUseCase.getFirstTimeVehicles(parkingId).stream()
                .map(v -> new ActiveVehicleResponse(
                        v.getId(),
                        v.getPlate(),
                        v.getEntryDate(),
                        v.getParking().getName()))
                .collect(Collectors.toList()));
    }

    @GetMapping("/socio/earnings/{socioId}")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<Map<String, Object>> getSocioEarnings(
            @PathVariable Long socioId,
            @RequestParam(defaultValue = "today") String period) {

        Double earnings = indicatorUseCase.getSocioEarnings(socioId, period);

        Map<String, Object> response = new HashMap<>();
        response.put("socioId", socioId);
        response.put("period", period);
        response.put("totalEarnings", earnings);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/parking/earnings/{parkingId}")
    @PreAuthorize("hasAuthority('VIEW_INDICATORS')")
    public ResponseEntity<Map<String, Object>> getParkingEarnings(
            @PathVariable Long parkingId,
            @RequestParam(defaultValue = "today") String period) {

        Double earnings = indicatorUseCase.getParkingEarnings(parkingId, period);

        Map<String, Object> response = new HashMap<>();
        response.put("parkingId", parkingId);
        response.put("period", period);
        response.put("totalEarnings", earnings);

        return ResponseEntity.ok(response);
    }
}
