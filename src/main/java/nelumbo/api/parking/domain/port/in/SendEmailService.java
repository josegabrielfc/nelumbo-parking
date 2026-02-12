package nelumbo.api.parking.domain.port.in;

public interface SendEmailService {
    String sendEmail(String email, String plate, String message, Long parkingId);
}
