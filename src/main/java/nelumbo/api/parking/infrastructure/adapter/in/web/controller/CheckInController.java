package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.domain.port.in.CheckInService;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CheckInRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CheckInResponse;

@RestController
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    @PreAuthorize("hasAuthority('REGISTER_ENTRY')")
    public ResponseEntity<CheckInResponse> registerEntry(
            @Valid @RequestBody CheckInRequest request,
            @AuthenticationPrincipal User currentUser) {

        VehicleRecord record = checkInService.registerEntry(request.plate(), request.parkingId(), currentUser);
        return new ResponseEntity<>(new CheckInResponse(record.getId()), HttpStatus.CREATED);
    }
}
