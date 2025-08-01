package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setId(generateUniqueId());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);
        user.setEmail(request.getEmail());
        user.setFullName(request.getFirstName() + " " + request.getLastName());
        user.setPasswordExpireDate(LocalDateTime.now().plusHours(8));//OTP entegre edilecek.
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        cacheToken(user.getUsername(), jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String cachedToken = getCachedToken(user.getUsername());

        // Token varsa ve süresi geçerli ise, onu döndür
        if (cachedToken != null && jwtService.isTokenValid(cachedToken, user)) {
            return new AuthenticationResponse(cachedToken);
        }

        //Token yoksa veya süresi dolmuşsa → yeni token üret
        String jwtToken = jwtService.generateToken(user);
        cacheToken(user.getUsername(), jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    private void cacheToken(String username, String token) {
        Cache cache = cacheManager.getCache("tokenCache");
        if (cache != null) {
            cache.put(username, token);
        }
    }

    private String getCachedToken(String username) {
        Cache cache = cacheManager.getCache("tokenCache");
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(username);
            if (wrapper != null) {
                return (String) wrapper.get();
            }
        }
        return null;
    }

    private Integer generateUniqueId() {
        return Math.toIntExact(System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}
