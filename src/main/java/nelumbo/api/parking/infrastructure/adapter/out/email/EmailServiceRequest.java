package nelumbo.api.parking.infrastructure.adapter.out.email;

public record EmailServiceRequest(
        String email,
        String placa,
        String mensaje,
        String parqueaderoNombre) {
}