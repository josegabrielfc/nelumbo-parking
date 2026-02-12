package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.in.ParkingService;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ParkingRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ParkingResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.mapper.ParkingMapper;

@RestController
@RequestMapping("/api/parkings")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<ParkingResponse> create(@Valid @RequestBody ParkingRequest request) {
        Parking parking = parkingMapper.toDomain(request);
        Parking created = parkingService.create(parking);
        return new ResponseEntity<>(parkingMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<ParkingResponse> update(@PathVariable Long id, @Valid @RequestBody ParkingRequest request) {
        Parking parking = parkingMapper.toDomain(request);
        Parking updated = parkingService.update(id, parking);
        return ResponseEntity.ok(parkingMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<List<ParkingResponse>> findAll() {
        return ResponseEntity.ok(parkingService.findAll().stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<ParkingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingMapper.toResponse(parkingService.findById(id)));
    }

    @GetMapping("/socio")
    @PreAuthorize("hasAuthority('VIEW_MY_PARKING')")
    public ResponseEntity<List<ParkingResponse>> findBySocio(
            @RequestParam(required = false) Long id,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(parkingService.findBySocioId(id, currentUser).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList()));
    }
}
