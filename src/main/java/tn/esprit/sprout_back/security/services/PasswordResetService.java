package tn.esprit.sprout_back.security.services;

import tn.esprit.sprout_back.models.User;
import tn.esprit.sprout_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Envoie un email de réinitialisation de mot de passe.
     *
     * @param email L'email de l'utilisateur.
     */
    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Générer un token de réinitialisation
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // Créer le lien de réinitialisation
        String resetLink = "http://localhost:8081/api/auth/reset-password?token=" + resetToken;

        // Envoyer l'email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);

        mailSender.send(message);
    }

    /**
     * Réinitialise le mot de passe de l'utilisateur.
     *
     * @param token       Le token de réinitialisation.
     * @param newPassword Le nouveau mot de passe.
     */
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Mettre à jour le mot de passe
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Supprimer le token après utilisation
        userRepository.save(user);
    }
}