package nelumbo.api.parking.domain.port.out;

public interface PasswordEncoderPort {
    String encode(String rawPassword);
}