package nelumbo.api.parking.domain.port.out;

public interface UserSessionRepositoryPort {
    void updateToken(String email, String token);
}
