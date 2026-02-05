package nelumbo.api.parking.domain.port.in;

public interface CheckOutUseCase {
    void registerExit(String plate, Long parkingId);
}
