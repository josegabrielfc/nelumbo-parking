package nelumbo.api.parking.infrastructure.adapter.out.email;

import lombok.extern.slf4j.Slf4j;
import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.port.out.EmailServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class EmailServiceAdapter implements EmailServicePort {
    private final RestTemplate restTemplate;
    private final String emailServiceUrl;

    public EmailServiceAdapter(
            RestTemplate restTemplate,
            @Value("${email.service.url:http://localhost:8081}") String emailServiceUrl) {
        this.restTemplate = restTemplate;
        this.emailServiceUrl = emailServiceUrl;
    }

    @Override
    public String sendEmail(String email, String plate, String message, String parkingName) {
        try {
            String url = emailServiceUrl + "/api/email/send";

            EmailServiceRequest request = new EmailServiceRequest(email, plate, message, parkingName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EmailServiceRequest> entity = new HttpEntity<>(request, headers);

            log.info("Enviando correo al microservicio: {}", url);

            ResponseEntity<EmailServiceResponse> response = restTemplate.postForEntity(
                    url,
                    entity,
                    EmailServiceResponse.class);

            if (response.getBody() != null) {
                log.info("Respuesta del microservicio: {}", response.getBody().mensaje());
                return response.getBody().mensaje();
            }

            return "Correo Enviado";

        } catch (RestClientException e) {
            log.error("Error al comunicarse con el microservicio de correo", e);
            throw new ApplicationException(ErrorCodes.EMAIL_SERVICE_ERROR);
        }
    }
}