package nelumbo.api.parking.infrastructure.adapter.in.web.mapper;

import nelumbo.api.parking.domain.model.Parking;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ParkingRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.ParkingResponse;
import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

    public Parking toDomain(ParkingRequest request) {
        Parking parking = new Parking();
        parking.setName(request.name());
        parking.setCapacity(request.capacity());
        parking.setCostPerHour(request.costPerHour());

        User socio = new User();
        socio.setId(request.socioId());
        parking.setSocio(socio);

        return parking;
    }

    public ParkingResponse toResponse(Parking parking) {
        return new ParkingResponse(
                parking.getId(),
                parking.getName(),
                parking.getCapacity(),
                parking.getCostPerHour(),
                parking.getSocio().getId(),
                parking.getSocio().getName());
    }
}
