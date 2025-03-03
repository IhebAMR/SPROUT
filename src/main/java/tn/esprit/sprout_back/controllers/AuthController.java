package tn.esprit.sprout_back.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import tn.esprit.sprout_back.models.*;
import tn.esprit.sprout_back.payload.request.*;
import tn.esprit.sprout_back.payload.response.*;
import tn.esprit.sprout_back.repository.*;
import tn.esprit.sprout_back.security.jwt.JwtUtils;
import tn.esprit.sprout_back.security.services.PasswordResetService;
import tn.esprit.sprout_back.security.services.UserDetailsImpl;
import tn.esprit.sprout_back.security.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*")
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

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PasswordResetService passwordResetService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Générer le cookie JWT
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        // Générer le token JWT pour la réponse
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())  // Ajouter le cookie dans l'en-tête de la réponse
                .body(new UserInfoResponse(
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getAuthorities().stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList()),
                        token
                ));
    }


    @GetMapping("/profile/{id}")
    //git@PreAuthorize("permitAll()")
    public ResponseEntity<?> getProfile(@PathVariable("id") String id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Profile not found."));
        }

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setBio(profile.getBio());
        profileResponse.setAvatar(profile.getAvatar());
        profileResponse.setContactInfo(profile.getContactInfo());
        profileResponse.setSkills(profile.getSkills());

        return ResponseEntity.ok(profileResponse);
    }
    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(devRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "dev":
                        Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(devRole);
                        break;
                    case "project_manager":
                        Role pmRole = roleRepository.findByName(ERole.ROLE_PROJECT_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(pmRole);
                        break;
                    default:
                        throw new RuntimeException("Error: Role '" + role + "' is not valid.");
                }
            });
        }

        user.setRoles(roles);

        // Créer le profil
        Profile profile = new Profile();

        // Définir les compétences si l'utilisateur a le rôle ROLE_DEV
        if (roles.stream().anyMatch(role -> role.getName() == ERole.ROLE_DEV)) {
            if (signUpRequest.getSkills() == null || signUpRequest.getSkills().isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Skills are required for developers."));
            }
            profile.setSkills(signUpRequest.getSkills());  // Définir les compétences avant d'enregistrer le profil
        }

        // Enregistrer le profil dans MongoDB
        profile = profileRepository.save(profile);

        // Associer le profil à l'utilisateur
        user.setProfile(profile);

        // Enregistrer l'utilisateur dans MongoDB
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        passwordResetService.sendPasswordResetEmail(email);
        return ResponseEntity.ok(new MessageResponse("Password reset email sent"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok(new MessageResponse("Password reset successfully"));
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }


    @PutMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileUpdateRequest profileUpdateRequest, @PathVariable("id") String id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Profile not found."));
        }
        profile.setBio(profileUpdateRequest.getBio());
        profile.setAvatar(profileUpdateRequest.getAvatar());
        profile.setContactInfo(profileUpdateRequest.getContactInfo());
        profile.setSkills(profileUpdateRequest.getSkills());
        profileRepository.save(profile);
        return ResponseEntity.ok(new MessageResponse("Profile updated successfully!"));
    }

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@PathVariable String userId, @RequestBody Set<String> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        Set<Role> newRoles = new HashSet<>();
        roles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    newRoles.add(adminRole);
                    break;
                case "dev":
                    Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    newRoles.add(devRole);
                    break;
                default:
                    throw new RuntimeException("Error: Invalid role.");
            }
        });
        user.setRoles(newRoles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Roles updated successfully!"));
    }
}
