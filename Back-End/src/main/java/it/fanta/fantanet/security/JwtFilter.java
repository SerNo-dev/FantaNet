package it.fanta.fantanet.security;


import it.fanta.fantanet.exception.UnauthorizedException;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtTool jwtTool;
    private UserService userService;

    private static final List<String> excludeUrlPatterns = List.of(
            "/api/voti/**",
            "/api/giocatori/**",
            "/api/giocatore/**",
            "/api/fixtures/**",
            "/api/team/**",
            "/auth/**"
    );

    @Autowired
    public void setJwtTool(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            jwtTool.verifyToken(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        }

        int userIdInt = jwtTool.getIdFromToken(token);
        Long userId = Long.valueOf(userIdInt);

        Optional<Utente> utenteOptional = userService.getUserById(userId);
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();

            Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        for (String pattern : excludeUrlPatterns) {
            if (new AntPathRequestMatcher(pattern).matches(request)) {
                return true;
            }
        }
        return false;
    }
}
