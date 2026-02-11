package nelumbo.api.parking.domain.port.out;

import nelumbo.api.parking.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);

    String getSubjectFromToken(String token);

    boolean validateToken(String token);
}
