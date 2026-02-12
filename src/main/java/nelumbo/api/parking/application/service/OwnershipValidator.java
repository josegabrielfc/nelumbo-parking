package nelumbo.api.parking.application.service;

import nelumbo.api.parking.domain.exception.ApplicationException;
import nelumbo.api.parking.domain.exception.ErrorCodes;
import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class OwnershipValidator {

    public void validate(Parking parking, User user) {
        boolean isAdmin = "ADMIN".equals(user.getRole().getName());

        if (!isAdmin) {
            if (!parking.getSocio().getId().equals(user.getId())) {
                throw new ApplicationException(ErrorCodes.PARKING_NOT_OWNED);
            }
        }
    }
}
