package tn.esprit.sprout_back.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import tn.esprit.sprout_back.models.Profile;
import tn.esprit.sprout_back.models.ERole;
import tn.esprit.sprout_back.models.Role;
import tn.esprit.sprout_back.models.User;
import tn.esprit.sprout_back.payload.request.LoginRequest;
import tn.esprit.sprout_back.payload.request.ProfileUpdateRequest;
import tn.esprit.sprout_back.payload.request.SignupRequest;
import tn.esprit.sprout_back.payload.response.MessageResponse;
import tn.esprit.sprout_back.payload.response.ProfileResponse;
import tn.esprit.sprout_back.payload.response.UserInfoResponse;
import tn.esprit.sprout_back.repository.ProfileRepository;
import tn.esprit.sprout_back.repository.RoleRepository;
import tn.esprit.sprout_back.repository.UserRepository;
import tn.esprit.sprout_back.security.jwt.JwtUtils;
import tn.esprit.sprout_back.security.services.PasswordResetService;
import tn.esprit.sprout_back.security.services.UserDetailsImpl;
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

@CrossOrigin(origins = "*", maxAge = 3600)
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
    private PasswordResetService passwordResetService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Générer le cookie et le token JWT
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Renvoie l'objet UserInfoResponse incluant le token
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles,
                        token
                ));
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Création du compte utilisateur
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Profile p = new Profile();
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(devRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "project_manager":
                        Role pmRole = roleRepository.findByName(ERole.ROLE_PROJECT_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(pmRole);
                        break;
                    case "dev":
                        Role dev = roleRepository.findByName(ERole.ROLE_DEV)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(dev);
                        break;
                    default:
                        throw new RuntimeException("Error: Role '" + role + "' is not valid.");
                }
            });
        }
        user.setRoles(roles);
        p = profileRepository.save(p);
        user.setProfile(p);
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Endpoint pour récupérer le profil de l'utilisateur connecté
    @GetMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfile(@PathVariable("id") String id) {

        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Profile not found."));
        }
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setBio(profile.getBio());
        profileResponse.setAvatar(profile.getAvatar());
        profileResponse.setContactInfo(profile.getContactInfo());
        return ResponseEntity.ok(profileResponse);
    }

    // Endpoint pour mettre à jour le profil de l'utilisateur connecté
    @PutMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileUpdateRequest profileUpdateRequest, @PathVariable("id") String id) {

        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Profile not found."));
        }
        profile.setBio(profileUpdateRequest.getBio());
        profile.setAvatar(profileUpdateRequest.getAvatar());
        profile.setContactInfo(profileUpdateRequest.getContactInfo());
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
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(adminRole);
                    break;
                case "project_manager":
                    Role pmRole = roleRepository.findByName(ERole.ROLE_PROJECT_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(pmRole);
                    break;
                case "dev":
                    Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(devRole);
                    break;
                default:
                    throw new RuntimeException("Error: Role '" + role + "' is not valid.");
            }
        });
        user.setRoles(newRoles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User roles updated successfully!"));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: User not found."));
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ROLE_DEV') or hasRole('ROLE_PROJECT_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/{userId}/skills")
    @PreAuthorize("hasRole('ROLE_DEV') or hasRole('ROLE_PROJECT_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addSkill(@PathVariable String userId, @RequestBody String skill) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        List<String> skills = user.getSkills();
        skills.add(skill);
        user.setSkills(skills);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Skill added successfully!"));
    }



    @PostMapping("/roles/{roleId}/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addPermissionToRole(@PathVariable String roleId, @RequestBody String permission) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        role.getPermissions().add(permission);
         roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse("Permission added to role successfully!"));
    }
    @PostMapping("/signup/google")
    public ResponseEntity<?> registerUserWithGoogle(@RequestParam String token) {
        // Valider le token Google et récupérer les informations de l'utilisateur
        // Vous pouvez utiliser une bibliothèque comme Google API Client pour cela

        // Exemple de récupération des informations de l'utilisateur
        String email = "user-email-from-google"; // Remplacer par l'email récupéré depuis Google
        String username = "user-username-from-google"; // Remplacer par le username récupéré depuis Google

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Création du compte utilisateur
        User user = new User(
                username,
                email,
                encoder.encode("random-password") // Vous pouvez générer un mot de passe aléatoire
        );

        Set<Role> roles = new HashSet<>();
        Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(devRole);
        user.setRoles(roles);

        Profile p = new Profile();
        p = profileRepository.save(p);
        user.setProfile(p);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully with Google!"));
    }
}
