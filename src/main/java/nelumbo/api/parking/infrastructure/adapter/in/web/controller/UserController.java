package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.in.CreateUserUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.UserRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.UserResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.mapper.UserRequestMapper;
import nelumbo.api.parking.infrastructure.adapter.in.web.mapper.UserResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @PostMapping("/socio")
    @PreAuthorize("hasAuthority('CREATE_SOCIO')")
    public ResponseEntity<UserResponse> createSocio(@Valid @RequestBody UserRequest userRequest) {
        User newUser = userRequestMapper.toDomain(userRequest);
        User createdUser = createUserUseCase.createSocio(newUser);
        return new ResponseEntity<>(userResponseMapper.toResponse(createdUser), HttpStatus.CREATED);
    }
}
