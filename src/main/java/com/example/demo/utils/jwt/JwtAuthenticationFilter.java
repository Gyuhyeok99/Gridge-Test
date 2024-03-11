package com.example.demo.utils.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.demo.utils.jwt.JwtProvider.HEADER_AUTHORIZATION;
import static com.example.demo.utils.jwt.JwtProvider.TOKEN_PREFIX;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        ///api/v1/auth 는 권한 필요 없는 api 이므로 바로 통과
        if (request.getServletPath().contains("/api/v1/auth") || request.getServletPath().contains("/api/v1/test")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        final String jwt;
        final String userId;

        // 헤더에 토큰이 없거나 Bearer 로 시작하지 않으면 통과
        if (authHeader == null ||!authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userId = jwtProvider.extractUserId(jwt);

        // 토큰이 유효하고 만료되지 않았다면 SecurityContext에 인증 정보를 저장
        // 토큰이 만료되지 않았는지는 JwtService에서 확인
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
            if (jwtProvider.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
