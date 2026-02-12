package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.port.in.SendEmailService;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.SendEmailRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.SendEmailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final SendEmailService sendEmailService;

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('SEND_EMAILS')")
    public ResponseEntity<SendEmailResponse> sendEmail(@Valid @RequestBody SendEmailRequest request) {
        String response = sendEmailService.sendEmail(
                request.email(),
                request.placa(),
                request.mensaje(),
                request.parqueaderoId());
        return ResponseEntity.ok(new SendEmailResponse(response));
    }
}
