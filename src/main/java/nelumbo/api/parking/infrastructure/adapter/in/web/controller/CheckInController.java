package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.in.CheckInUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CheckInRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CheckInResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInUseCase checkInUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('REGISTER_ENTRY')")
    public ResponseEntity<CheckInResponse> registerEntry(@Valid @RequestBody CheckInRequest request) {
        VehicleRecord record = checkInUseCase.registerEntry(request.plate(), request.parkingId());
        return new ResponseEntity<>(new CheckInResponse(record.getId()), HttpStatus.CREATED);
    }
}
