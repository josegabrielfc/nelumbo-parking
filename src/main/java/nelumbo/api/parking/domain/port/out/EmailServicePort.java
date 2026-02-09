package nelumbo.api.parking.domain.port.out;

public interface EmailServicePort {
    String sendEmail(String email, String plate, String message, String parkingName);
}