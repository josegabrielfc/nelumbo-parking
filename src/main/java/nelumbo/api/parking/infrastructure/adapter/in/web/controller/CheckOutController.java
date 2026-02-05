package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.port.in.CheckOutUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CheckOutRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/check-out")
@RequiredArgsConstructor
public class CheckOutController {

    private final CheckOutUseCase checkOutUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('REGISTER_EXIT')")
    public ResponseEntity<Map<String, String>> registerExit(@Valid @RequestBody CheckOutRequest request) {
        checkOutUseCase.registerExit(request.plate(), request.parkingId());

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Salida registrada");

        return ResponseEntity.ok(response);
    }
}
