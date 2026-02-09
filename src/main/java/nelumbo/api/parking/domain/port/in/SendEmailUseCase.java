package nelumbo.api.parking.domain.port.in;

public interface SendEmailUseCase {
    String sendEmail(String email, String plate, String message, Long parkingId);
}
