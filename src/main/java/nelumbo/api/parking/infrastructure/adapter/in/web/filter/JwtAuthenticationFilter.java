package nelumbo.api.parking.infrastructure.adapter.in.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nelumbo.api.parking.domain.model.User;
import nelumbo.api.parking.domain.port.out.TokenProviderPort;
import nelumbo.api.parking.domain.port.out.UserRepositoryPort;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProviderPort tokenProviderPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userIdStr = tokenProviderPort.getSubjectFromToken(jwt);

        if (userIdStr != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Long userId = Long.parseLong(userIdStr);
            User user = userRepositoryPort.findById(userId).orElse(null);
            if (user != null && jwt.equals(user.getToken()) && tokenProviderPort.validateToken(jwt)) {

                List<GrantedAuthority> authorities = new ArrayList<>();
                // Agregar Rol
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));

                // Agregar Permisos
                if (user.getRole().getPermissions() != null) {
                    user.getRole().getPermissions()
                            .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                        null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
