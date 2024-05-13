package ch.wiss.adressverwaltung.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.adressverwaltung.models.ERole;
import ch.wiss.adressverwaltung.models.Role;
import ch.wiss.adressverwaltung.models.User;
import ch.wiss.adressverwaltung.repository.RoleRepository;
import ch.wiss.adressverwaltung.repository.UserRepository;
import ch.wiss.adressverwaltung.request.LoginRequest;
import ch.wiss.adressverwaltung.request.SignupRequest;
import ch.wiss.adressverwaltung.security.JwtResponse;
import ch.wiss.adressverwaltung.security.JwtUtils;
import ch.wiss.adressverwaltung.security.UserDetailsImpl;
import ch.wiss.adressverwaltung.security.MessageResponse;
import jakarta.validation.Valid;

@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRole(ERole.ROLE_MODERATION)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/address")
public ResponseEntity<?> addAddressToUser(@RequestBody Address address) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Error: User not found."));
    user.setAddress(address);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("Address added successfully!"));
}

@GetMapping("/address")
public ResponseEntity<?> getUserAddress() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Error: User not found."));
    Address address = user.getAddress();
    return ResponseEntity.ok(address);
}

@PutMapping("/address")
public ResponseEntity<?> updateUserAddress(@RequestBody Address address) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Error: User not found."));
    user.setAddress(address);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("Address updated successfully!"));
}

@DeleteMapping("/address")
public ResponseEntity<?> removeUserAddress() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Error: User not found."));
    user.setAddress(null);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("Address removed successfully!"));
}

}