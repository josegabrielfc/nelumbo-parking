package nelumbo.api.parking.infrastructure.adapter.in.web.mapper;

import nelumbo.api.parking.domain.model.VehicleRecord;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.VehicleRecordDetailResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.VehicleRecordDetailResponse.ParkingDetailResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.VehicleRecordDetailResponse.SocioDetailResponse;
import org.springframework.stereotype.Component;

@Component
public class VehicleRecordMapper {

    public VehicleRecordDetailResponse toDetailResponse(VehicleRecord record) {
        return new VehicleRecordDetailResponse(
                record.getId(),
                record.getPlate(),
                record.getEntryDate(),
                new ParkingDetailResponse(
                        record.getParking().getId(),
                        record.getParking().getName(),
                        record.getParking().getCapacity(),
                        record.getParking().getCostPerHour(),
                        new SocioDetailResponse(
                                record.getParking().getSocio().getId(),
                                record.getParking().getSocio().getName())));
    }
}
