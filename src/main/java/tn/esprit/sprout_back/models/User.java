package tn.esprit.sprout_back.models;

import java.util.HashSet;
<<<<<<< HEAD
import java.util.Set;


=======
import java.util.List;
import java.util.Set;

>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

<<<<<<< HEAD
    private String resetToken; // Ajout du champ pour le token de réinitialisation

=======
    private List<String> skills; // Compétences de l'utilisateur
    private List<String> certifications; // Certifications de l'utilisateur

    @DBRef
    private Profile profile; // Référence au profil de l'utilisateur

    private String resetToken; // Token pour la réinitialisation du mot de passe
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

<<<<<<< HEAD
=======
    // Getters et Setters pour tous les attributs
>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

<<<<<<< HEAD
    // Getters et Setters pour resetToken
=======
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

>>>>>>> b979afa127bbd0ca73396ab069e703bcb0ea14d2
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}