package nelumbo.api.parking.domain.port.out;

import java.util.List;

import nelumbo.api.parking.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);

    String getEmailFromToken(String token);

    boolean validateToken(String token);

    List<String> getAuthoritiesFromToken(String token);
}
