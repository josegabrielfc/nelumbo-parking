package nelumbo.email.service;

import lombok.extern.slf4j.Slf4j;
import nelumbo.email.dto.EmailRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public void sendEmail(EmailRequest request) {
        // Simulación de envío de correo
        log.info("========================================");
        log.info("SIMULACION DE ENVIO DE CORREO");
        log.info("========================================");
        log.info("Destinatario: {}", request.email());
        log.info("Placa: {}", request.placa());
        log.info("Parqueadero: {}", request.parqueaderoNombre());
        log.info("Mensaje: {}", request.mensaje());
        log.info("========================================");
        log.info("Correo enviado exitosamente");
        log.info("========================================");
    }
}
