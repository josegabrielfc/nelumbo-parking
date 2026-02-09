package nelumbo.email.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.email.dto.EmailRequest;
import nelumbo.email.dto.EmailResponse;
import nelumbo.email.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest request) {
        emailService.sendEmail(request);
        return ResponseEntity.ok(new EmailResponse("Correo Enviado"));
    }
}