package nelumbo.api.parking.infrastructure.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.in.UserUseCase;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.UserRequest;
import nelumbo.api.parking.infrastructure.adapter.in.web.dto.UserResponse;
import nelumbo.api.parking.infrastructure.adapter.in.web.mapper.UserRequestMapper;
import nelumbo.api.parking.infrastructure.adapter.in.web.mapper.UserResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @PostMapping("/socio")
    @PreAuthorize("hasAuthority('CREATE_SOCIO')")
    public ResponseEntity<UserResponse> createSocio(@Valid @RequestBody UserRequest userRequest) {
        User newUser = userRequestMapper.toDomain(userRequest);
        User createdUser = userUseCase.createSocio(newUser);
        return new ResponseEntity<>(userResponseMapper.toResponse(createdUser), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userUseCase.findAll()
                .stream()
                .map(userResponseMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userUseCase.findById(id);
        return ResponseEntity.ok(userResponseMapper.toResponse(user));
    }

    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable Long roleId) {
        List<UserResponse> users = userUseCase.findByRole(roleId)
                .stream()
                .map(userResponseMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
