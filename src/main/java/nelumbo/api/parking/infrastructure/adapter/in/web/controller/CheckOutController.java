package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import nelumbo.api.parking.domain.port.in.CheckOutService;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.CheckOutRequest;

@RestController
@RequestMapping("/api/check-out")
@RequiredArgsConstructor
public class CheckOutController {

    private final CheckOutService checkOutService;

    @PostMapping
    @PreAuthorize("hasAuthority('REGISTER_EXIT')")
    public ResponseEntity<Map<String, String>> registerExit(
            @Valid @RequestBody CheckOutRequest request,
            @AuthenticationPrincipal User currentUser) {

        checkOutService.registerExit(request.plate(), request.parkingId(), currentUser);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Salida registrada");

        return ResponseEntity.ok(response);
    }
}
