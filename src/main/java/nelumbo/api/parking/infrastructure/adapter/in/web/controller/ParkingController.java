package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.port.in.ParkingUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ParkingRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ParkingResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.mapper.ParkingMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parkings")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingUseCase parkingUseCase;
    private final ParkingMapper parkingMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<ParkingResponse> create(@Valid @RequestBody ParkingRequest request) {
        Parking parking = parkingMapper.toDomain(request);
        Parking created = parkingUseCase.create(parking);
        return new ResponseEntity<>(parkingMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<ParkingResponse> update(@PathVariable Long id, @Valid @RequestBody ParkingRequest request) {
        Parking parking = parkingMapper.toDomain(request);
        Parking updated = parkingUseCase.update(id, parking);
        return ResponseEntity.ok(parkingMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_PARKING')")
    public ResponseEntity<List<ParkingResponse>> findAll() {
        return ResponseEntity.ok(parkingUseCase.findAll().stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_PARKING') or hasAuthority('VIEW_MY_PARKING')")
    public ResponseEntity<ParkingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingMapper.toResponse(parkingUseCase.findById(id)));
    }

    @GetMapping("/socio/{socioId}")
    @PreAuthorize("hasAuthority('VIEW_MY_PARKING')")
    public ResponseEntity<List<ParkingResponse>> findBySocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(parkingUseCase.findBySocioId(socioId).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList()));
    }
}
